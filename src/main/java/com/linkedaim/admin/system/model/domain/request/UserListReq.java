package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 用户列表查询请求实体
 * @date 2019-07-18
 */
@Data
public class UserListReq implements Serializable {

    @ApiModelProperty(value = "状态：0-锁定 2-启用")
    private String status;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "账号：唯一标识")
    private String account;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value="每页记录数（10）")
    @NotNull(message = "每页记录数不能为NULL")
    @DecimalMin(value = "1",message = "每页记录数必须大于等于{value}")
    private Integer pageSize;

    @ApiModelProperty(value="页码")
    @NotNull(message = "页码不能为NULL")
    @DecimalMin(value = "0",message = "页码必须大于等于{value}")
    private Integer pageNo;
}
