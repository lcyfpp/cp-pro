package com.linkedaim.admin.system.model.domain.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVO implements Serializable {

    @ApiModelProperty(value = "菜单/按钮id")
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

    @ApiModelProperty(value = "排序")
    private Long orderNum;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "下级菜单列表")
    private List<MenuVO> childList;

}
