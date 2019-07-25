package com.linkedaim.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author zhaoyangyang
 * @title swagger2配置
 * @date 2019-07-18
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linkedaim"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title("后台权限管理系统接口")
                .description("cp-admin")
                .contact(new Contact("linkedaim", "https://www.linkedaim.com/", "zhaoyy_z@163.com"))
                .version("1.0.0")
                .build();
    }
}
