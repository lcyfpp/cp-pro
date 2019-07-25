package com.linkedaim.admin.system.model.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 菜单编辑请求实体
 * @date 2019-07-18
 */
@Data
public class MenuEditReq implements Serializable {

    @ApiModelProperty(value = "菜单/按钮id",notes = "修改必传")
    private Long id;

    @ApiModelProperty(value = "上级菜单id")
    private Long parentId;

    @ApiModelProperty(value = "菜单/按钮名称")
    private String menuName;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "类型 0菜单 1按钮")
    private String type;

    @ApiModelProperty(value = "状态 1-启用 0-禁用")
    private String status;

    @ApiModelProperty(value = "排序")
    private Long orderNum;
}
