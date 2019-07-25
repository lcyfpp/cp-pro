package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 授权角色请求实体
 * @date 2019-07-20
 */
@Data
public class AuthorizeRoleReq implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色列表")
    private List<Long> roleIds;
}
