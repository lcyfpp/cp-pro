package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 角色列表请求实体
 * @date 2019-07-18
 */
@Data
public class RoleListReq implements Serializable {

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

    @ApiModelProperty(value="每页记录数（10）")
    @NotNull(message = "每页记录数不能为NULL")
    @DecimalMin(value = "1",message = "每页记录数必须大于等于{value}")
    private Integer pageSize;

    @ApiModelProperty(value="页码")
    @NotNull(message = "页码不能为NULL")
    @DecimalMin(value = "0",message = "页码必须大于等于{value}")
    private Integer pageNo;
}
