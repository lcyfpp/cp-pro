package com.linkedaim.admin.system.model.domain.convent;

import com.google.common.collect.Lists;
import com.linkedaim.admin.system.model.domain.response.MenuVO;
import com.linkedaim.admin.system.model.entity.Menu;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 对象装换
 * @date 2019-07-02
 */

public class MenuConvert implements Serializable {

    /**
     * 菜单集合转换
     * @param menus
     * @return
     */
    public static List<MenuVO> menuToMenuVO(List<Menu> menus) {
        List<MenuVO> menuVOList = Lists.newArrayList();
        menus.forEach(menu -> {
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(menu, menuVO);
            menuVOList.add(menuVO);
        });
        return menuVOList;
    }
}
