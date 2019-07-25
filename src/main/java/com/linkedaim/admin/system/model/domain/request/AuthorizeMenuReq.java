package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 角色授权菜单请求实体
 * @date 2019-07-20
 */
@Data
public class AuthorizeMenuReq implements Serializable {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单列表")
    private List<Long> menuIds;
}
