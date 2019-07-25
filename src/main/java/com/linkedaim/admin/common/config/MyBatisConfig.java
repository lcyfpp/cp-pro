package com.linkedaim.admin.common.config;

import com.linkedaim.admin.common.interceptor.SqlStatementInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaoyangyang
 * @title MyBatisConfig配置
 * @date 2019-07-18
 */
@Configuration
public class MyBatisConfig {

    /**
     * 配置 sql打印拦截器
     * application.yml中 kinkedaim.showsql: true 时生效
     * @return SqlStatementInterceptor
     */
    @Bean
    @ConditionalOnProperty(name = "kinkedaim.showsql", havingValue = "true")
    SqlStatementInterceptor sqlStatementInterceptor() {
        return new SqlStatementInterceptor();
    }
}
