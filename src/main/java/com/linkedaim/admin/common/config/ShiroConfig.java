package com.linkedaim.admin.common.config;

import com.linkedaim.admin.common.cache.ShiroCacheManager;
import com.linkedaim.admin.common.filter.JwtFilter;
import com.linkedaim.admin.common.filter.OutFilter;
import com.linkedaim.admin.common.shiro.ShiroRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro配置
 * @author zhaoyangyang
 * @date 2019-07-18
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置使用自定义Realm，关闭Shiro自带的session
     * 详情见文档 http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @author zhaoyangyang
     * @date 2019-07-18
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自定义Realm
        manager.setRealm(shiroRealm());
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        // 设置自定义Cache缓存
        manager.setCacheManager(new ShiroCacheManager());
        return manager;
    }

    @Bean
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    /**
     * 添加自己的过滤器，自定义url规则
     * 详情见文档 http://shiro.apache.org/web.html#urls-
     * @param securityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author zhaoyangyang
     * @date 2019-07-18
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt", new JwtFilter());
        filterMap.put("logout", new OutFilter());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 自定义url规则
        Map<String, String> filterRuleMap = new HashMap<>(16);
        // 放行登录拦截
        filterRuleMap.put("/login/**", "anon");
        filterRuleMap.put("/project", "anon");
        // 放行swagger拦截
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/v2/**", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/configuration/**", "anon");

        // 放行退出拦截
        filterRuleMap.put("/logout", "logout");
        // 所有请求通过我们自己的JWTFilter
//        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
