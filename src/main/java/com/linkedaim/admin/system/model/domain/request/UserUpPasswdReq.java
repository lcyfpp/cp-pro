package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 修改密码请求实体
 * @date 2019-07-18
 */
@Data
public class UserUpPasswdReq implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "旧密码")
    private String oldpassword;

    @ApiModelProperty(value = "新密码")
    private String newpassword;
}
