package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 角色编辑请求实体
 * @date 2019-07-18
 */
@Data
public class RoleEditReq implements Serializable {

    @ApiModelProperty(value = "角色id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String remark;

    @ApiModelProperty(value = "状态 1-启用 0-禁用")
    private String status;
}
