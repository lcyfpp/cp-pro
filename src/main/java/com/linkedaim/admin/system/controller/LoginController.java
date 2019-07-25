package com.linkedaim.admin.system.controller;

import com.linkedaim.admin.common.aop.WriteLog;
import com.linkedaim.admin.common.config.LinkedaimProperties;
import com.linkedaim.admin.common.constant.LinkedaimResultEnum;
import com.linkedaim.admin.common.constant.ResultEnum;
import com.linkedaim.admin.common.constant.ResultResponse;
import com.linkedaim.admin.common.constant.SystemConstant;
import com.linkedaim.admin.common.exception.CustomException;
import com.linkedaim.admin.common.util.JedisUtil;
import com.linkedaim.admin.common.util.JwtUtil;
import com.linkedaim.admin.system.constants.BuinessEnum;
import com.linkedaim.admin.system.model.domain.request.LoginReq;
import com.linkedaim.admin.system.model.entity.User;
import com.linkedaim.admin.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "用户登录接口")
@Slf4j
@RestController
public class LoginController {
    @Autowired
    private LinkedaimProperties ledgerProperties;
    @Autowired
    private UserService userService;

    @WriteLog
    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping("/login")
    public ResultResponse login(@RequestBody @Valid LoginReq req, HttpServletResponse httpServletResponse) {
        try {
            // 查询数据库中的帐号信息
            User user = new User();
            user.setUsername(req.getUsername());
            User userDto = userService.selectOne(user);
            if (userDto == null) {
                throw new CustomException(LinkedaimResultEnum.NOUSER_FAIL.getMessage());
            }
            //验证密码
            String password = DigestUtils.md5DigestAsHex(req.getPassword().getBytes());
            if (!password.equals(userDto.getPassword())) {
                throw new CustomException(LinkedaimResultEnum.LOGIN_FAIL.getMessage());
            }
            //账号是否锁定
            if (BuinessEnum.userLockStatusEnum.LOCK.getCode().equals(userDto.getStatus())) {
                throw new CustomException(LinkedaimResultEnum.ACCOUNT_FAIL.getMessage());
            }
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(SystemConstant.PREFIX_SHIRO_CACHE + userDto.getAccount())) {
                JedisUtil.delKey(SystemConstant.PREFIX_SHIRO_CACHE + userDto.getAccount());
            }
            // 清楚用户登录信息
            if (JedisUtil.exists(SystemConstant.PREFIX_USER_LOGIN + userDto.getAccount())) {
                JedisUtil.delKey(SystemConstant.PREFIX_USER_LOGIN + userDto.getAccount());
            }
            // 设置新的登录信息 缓存1小时
            JedisUtil.setObject(SystemConstant.PREFIX_USER_LOGIN + userDto.getAccount(), userDto, 60 * 60 * 60);
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(SystemConstant.PREFIX_SHIRO_REFRESH_TOKEN + userDto.getAccount(), currentTimeMillis, Integer.parseInt(ledgerProperties.getRefreshTokenExpireTime()));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(userDto.getAccount(), userDto.getId().toString(), currentTimeMillis);
            httpServletResponse.setHeader(SystemConstant.REQUEST_AUTH_HEADER, token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", SystemConstant.REQUEST_AUTH_HEADER);
            return ResultResponse.ok(ResultEnum.SUCCESS);
        } catch (CustomException e) {
            return ResultResponse.fail(ResultEnum.CALL_FAIL.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("用户登录异常", e);
            return ResultResponse.fail(ResultEnum.SERVICE_INTERN_FAILURE);
        }
    }
}
