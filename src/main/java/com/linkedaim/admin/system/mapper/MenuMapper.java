package com.linkedaim.admin.system.mapper;

import com.linkedaim.admin.common.mapper.BaseMapper;
import com.linkedaim.admin.system.model.entity.Menu;
import com.linkedaim.admin.system.model.entity.dto.MenuDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取用户权限列表
     * @param userId
     * @return
     */
    List<Menu> getUserPermList(Long userId);

    /**
     * 获取用户菜单列表
     * @param userId
     * @return
     */
    List<MenuDTO> getUserMenuList(Long userId);

    /**
     * 获取用户子菜单列表
     * @param id
     * @return
     */
    List<MenuDTO> getUserMenuChildList(Long id);

    /**
     * 获取子菜单列表
     * @param id
     * @return
     */
    List<MenuDTO> getMenuChildList(@Param("id") Long id,@Param("status") String status);

    /**
     * 获取所有菜单列表
     * @return
     */
    List<MenuDTO> getMenuAllList(@Param("status") String status);
}
