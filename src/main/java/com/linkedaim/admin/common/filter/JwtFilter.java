package com.linkedaim.admin.common.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.linkedaim.admin.common.constant.ResultResponse;
import com.linkedaim.admin.common.entity.JwtToken;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.constant.SystemConstant;
import com.linkedaim.admin.common.util.JedisUtil;
import com.linkedaim.admin.common.util.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * JWT过滤
 * @author zhaoyangyang
 * @date 2019-07-18
 */
@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private static String refreshTokenExpireTime;

    @Value("${linkedaim.refreshTokenExpireTime}")
    public void setRefreshTokenExpireTime(String refreshTokenExpireTime) {
        JwtFilter.refreshTokenExpireTime = refreshTokenExpireTime;
    }


    //preHandle->isAccessAllowed->isLoginAttempt->executeLogin

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 判断用户是否想要登入
        if (this.isLoginAttempt(request, response)) {
            try {
                // 进行Shiro的登录UserRealm
                this.executeLogin(request, response);
            } catch (Exception e) {
                // 认证出现异常，传递错误信息msg
                String msg = e.getMessage();
                // 获取应用异常(该Cause是导致抛出此throwable(异常)的throwable(异常))
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    logger.warn("Token不合法(" + throwable.getMessage() + ")");
                    // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                    msg = "Token不合法(" + throwable.getMessage() + ")";
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        logger.warn("Token已过期(" + throwable.getMessage() + ")");
                        msg = "Token已过期，请重新登录(" + throwable.getMessage() + ")";
                    }
                } else {
                    // 应用异常不为空
                    if (throwable != null) {
                        // 获取应用异常msg
                        logger.warn("throwable(" + throwable.getMessage() + ")");
                        msg = throwable.getMessage();
                    }
                }
                // 直接返回Response信息
                this.response401(response, msg);
                return false;
            }
        } else {
            String msg = "用户未登录";
            this.response401(response, msg);
            return false;
        }
        return true;
    }

    /**
     * 检测Header里面是否包含Authorization字段，有就进行Token登录认证授权
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        return token != null;
    }

    /**
     * 进行AccessToken登录认证授权
     */
    @Override
    protected boolean executeLogin(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        String authorization = request.getHeader(SystemConstant.REQUEST_AUTH_HEADER);
        //JwtToken token = new JwtToken(this.getAuthzHeader(request));
        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(req, resp).login(token);
        // 绑定上下文
        String account = JwtUtil.getClaim(authorization, SystemConstant.ACCOUNT);
        String userId = JwtUtil.getClaim(authorization, SystemConstant.USERID);
        Long userIdL = Long.parseLong(userId);
        UserContext userContext = new UserContext(new LoginUser(userIdL, account));
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 此处为AccessToken刷新，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest req, ServletResponse resp) {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(req);
        // 获取当前Token的帐号信息
        String account = JwtUtil.getClaim(token, SystemConstant.ACCOUNT);
        String userId = JwtUtil.getClaim(token, SystemConstant.USERID);
        // 判断Redis中RefreshToken是否存在
        if (JedisUtil.exists(SystemConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(SystemConstant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新
            if (JwtUtil.getClaim(token, SystemConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                // 获取当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                // 读取配置文件，获取refreshTokenExpireTime属性
                String refreshTokenExpireTimeNew = refreshTokenExpireTime;
                // 设置RefreshToken中的时间戳为当前最新时间戳，且刷新过期时间重新为30分钟过期(配置文件可配置refreshTokenExpireTime属性)
                JedisUtil.setObject(SystemConstant.PREFIX_SHIRO_REFRESH_TOKEN + account, currentTimeMillis, Integer.parseInt(refreshTokenExpireTimeNew));
                // 刷新AccessToken，设置时间戳为当前最新时间戳
                token = JwtUtil.sign(account, userId, currentTimeMillis);
                // 将新刷新的AccessToken再次进行Shiro的登录
                JwtToken jwtToken = new JwtToken(token);
                // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获，如果没有抛出异常则代表登入成功，返回true
                this.getSubject(req, resp).login(jwtToken);
                // 最后将刷新的AccessToken存放在Response的Header中的Authorization字段返回
                HttpServletResponse response = (HttpServletResponse) resp;
                response.setHeader("Authorization", token);
                response.setHeader("Access-Control-Expose-Headers", "Authorization");
                this.responseExpire(response, "Token已过期");
                return true;
            }
        }
        return false;
    }

    /**
     * 这里我们详细说明下为什么重写
     * 可以对比父类方法，只是将executeLogin方法调用去除了
     * 如果没有去除将会循环调用doGetAuthenticationInfo方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    /**
     * 无需转发，直接返回Response信息
     */
    private void response401(ServletResponse resp, String msg) {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            ResultResponse result = ResultResponse.fail("401", msg);
            logger.warn("response401: {}", JSON.toJSONString(result));
            out.append(JSON.toJSONString(result));
            out.close();
        } catch (IOException e) {
            logger.error("直接返回Response信息出现IOException异常:" + e.getMessage());
            throw new CustomException("直接返回Response信息出现IOException异常");
        }
    }

    /**
     * token已过期请使用新的token
     */
    private void responseExpire(HttpServletResponse response, String msg) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            ResultResponse result = ResultResponse.fail("403", msg);
            logger.warn("responseExpire: {}", JSON.toJSONString(result));
            out.append(JSON.toJSONString(result));
            out.close();
        } catch (IOException e) {
            logger.error("直接返回Response信息出现IOException异常:" + e.getMessage());
            throw new CustomException("直接返回Response信息出现IOException异常");
        }
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(req, resp);
    }
}
