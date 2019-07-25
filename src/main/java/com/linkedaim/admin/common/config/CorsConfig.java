package com.linkedaim.admin.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoyangyang
 * @title 跨域配置
 * @date 2019-07-18
 */
@Slf4j
@Component
public class CorsConfig implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("当前请求的Method: {} ==>{}", httpRequest.getMethod(), httpRequest.getRequestURI());
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setStatus(HttpStatus.OK.value());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
}
