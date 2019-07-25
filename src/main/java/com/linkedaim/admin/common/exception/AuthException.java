package com.linkedaim.admin.common.exception;

import com.alibaba.fastjson.JSON;
import com.linkedaim.admin.common.constant.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhaoyangyang
 * @title 自定义异常
 * @date 2019-07-18
 */
@Slf4j
@ControllerAdvice
public class AuthException {

    @ExceptionHandler(value = UnauthorizedException.class)//处理访问方法时权限不足问题
    public String defaultErrorHandler(HttpServletRequest req, Exception e, HttpServletResponse response) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            //为了系统安全,只让用户看自定义异常错误信息
            if (e instanceof UnauthorizedException) {
                log.info("对不起，没有权限操作,错误内容:" + e.getMessage());
                ResultResponse error = ResultResponse.fail("401", "对不起，没有权限操作!");
                writer.write(JSON.toJSONString(error));
            } else {
                log.info("系统异常,请联系技术人员,错误内容:" + e.getMessage());
                ResultResponse error = ResultResponse.fail("500", "系统异常,请联系技术人员!");
                writer.write(JSON.toJSONString(error));
            }
            writer.flush();
            writer.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
