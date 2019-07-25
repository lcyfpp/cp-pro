package com.linkedaim.admin.system.model.entity.dto;

import com.linkedaim.admin.system.model.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 菜单对象
 * @date 2019-07-05
 */
@Data
public class MenuDTO extends Menu implements Serializable {

    /**
     * 下级菜单列表
     */
    private List<Menu> childList;
}
