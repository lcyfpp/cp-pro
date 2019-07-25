package com.linkedaim.admin.system.model.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = " 账号：唯一标识")
    private String account;

    @ApiModelProperty(value = "昵称，app用户")
    private String nickName;

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

    @ApiModelProperty(value = "用户类型 1-web 2-app")
    private String userType;

    @ApiModelProperty(value = "QQ登录id")
    private String qqOpenId;

    @ApiModelProperty(value = "微信登录id")
    private String wechatOpenId;

    @ApiModelProperty(value = "最近访问时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

}
