package com.linkedaim.admin.common.filter;

import com.alibaba.fastjson.JSON;
import com.linkedaim.admin.common.constant.ResultEnum;
import com.linkedaim.admin.common.constant.ResultResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhaoyangyang
 * @title 登出过滤器
 * @date 2019-07-18
 */
public class OutFilter extends LogoutFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        try {
            subject.logout();
        } catch (Exception ex) {
            LOGGER.error("退出登录错误", ex);
        }
        this.writeResult(response);
        //不执行后续的过滤器
        return false;
    }

    private void writeResult(ServletResponse response) {
        //响应Json结果
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            ResultResponse result = ResultResponse.ok(ResultEnum.SUCCESS);
            out.append(JSON.toJSONString(result));
        } catch (IOException e) {
            LOGGER.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
