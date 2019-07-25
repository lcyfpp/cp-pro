package com.linkedaim.admin.system.model.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleVO implements Serializable {

    @ApiModelProperty(value = "角色ID")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    private String remark;

}
