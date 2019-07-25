package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 用户信息修改实体
 * @date 2019-07-18
 */
@Data
public class UserEditReq implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号：唯一标识")
    private String account;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "状态 0锁定 1有效")
    private String status;

    @ApiModelProperty(value = "性别 0男 1女")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
