package com.linkedaim.admin.common.shiro;

import com.alibaba.fastjson.JSON;
import com.linkedaim.admin.system.model.entity.Menu;
import com.linkedaim.admin.common.constant.SystemConstant;
import com.linkedaim.admin.common.entity.JwtToken;
import com.linkedaim.admin.common.entity.RolesPerms;
import com.linkedaim.admin.common.util.JedisUtil;
import com.linkedaim.admin.common.util.JwtUtil;
import com.linkedaim.admin.common.util.StringUtil;
import com.linkedaim.admin.system.model.entity.Role;
import com.linkedaim.admin.system.model.entity.User;
import com.linkedaim.admin.system.service.MenuService;
import com.linkedaim.admin.system.service.RoleService;
import com.linkedaim.admin.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 * @author zhaoyangyang
 */
@Slf4j
@Component("shiroRealm")
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权模块，获取用户角色和权限
     * @param principals principal
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String userId = JwtUtil.getClaim(principals.toString(), SystemConstant.USERID);
        String account = JwtUtil.getClaim(principals.toString(), SystemConstant.ACCOUNT);
        // 获取用户角色集 获取用户权限集 先缓存后库
        RolesPerms rolesPerms = null;
        if (JedisUtil.exists(SystemConstant.PREFIX_SHIRO_CACHE + account)) {
            rolesPerms = (RolesPerms) JedisUtil.getObject(SystemConstant.PREFIX_SHIRO_CACHE + account);
            log.info("当前用户角色权限列表：{}", JSON.toJSONString(rolesPerms));
        }
        if (rolesPerms != null) {
            Set<String> roleSet = rolesPerms.getRoles();
            Set<String> permissionSet = rolesPerms.getStringPermissions();
            simpleAuthorizationInfo.setRoles(roleSet);
            simpleAuthorizationInfo.setStringPermissions(permissionSet);
        } else {
            List<Role> roleList = this.roleService.getUserRoleList(Long.parseLong(userId));
            Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
            List<Menu> permissionList = this.menuService.getUserPermList(Long.parseLong(userId));
            Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
            simpleAuthorizationInfo.setRoles(roleSet);
            simpleAuthorizationInfo.setStringPermissions(permissionSet);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证
     * @param auth AuthenticationToken 身份认证 token
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, SystemConstant.ACCOUNT);
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空");
        }
        // 查询用户是否存在 先缓存 后库
        User user = (User) JedisUtil.getObject(SystemConstant.PREFIX_USER_LOGIN + account);
        if (user == null) {
            user = new User();
            user.setAccount(account);
            user = userService.selectOne(user);
        }
        if (user.getId() == null) {
            throw new AuthenticationException("该帐号不存在");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(SystemConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(SystemConstant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, SystemConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期");
    }


    /**
     * 清除权限缓存
     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
     * 然后调用其clearCache方法。
     */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

}
