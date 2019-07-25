package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 登录请求实体
 * @date 2019-03-06
 */
@Data
public class LoginReq implements Serializable {

    @ApiModelProperty(value = "账号")
    @NotNull(message = "请输入账号")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "请输入密码")
    private String password;
}
