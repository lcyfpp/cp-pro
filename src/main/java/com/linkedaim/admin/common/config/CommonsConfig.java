package com.linkedaim.admin.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @author zhaoyangyang
 * @title 文件上传配置
 * @date 2019-07-18
 */
@Configuration
@ComponentScan({"com.linkedaim.*"})
public class CommonsConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(1024L * 1024L * 50);
        return factory.createMultipartConfig();
    }
}
