package com.linkedaim.admin.system.service;

import com.google.common.collect.Lists;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.service.impl.BaseServiceImpl;
import com.linkedaim.admin.system.constants.BuinessEnum;
import com.linkedaim.admin.system.mapper.MenuMapper;
import com.linkedaim.admin.system.model.domain.request.MenuEditReq;
import com.linkedaim.admin.system.model.domain.response.MenuVO;
import com.linkedaim.admin.system.model.entity.Menu;
import com.linkedaim.admin.system.model.entity.dto.MenuDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuService extends BaseServiceImpl<Menu> {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取用户权限列表
     * @param userId
     * @return
     */
    public List<Menu> getUserPermList(Long userId) {
        return this.menuMapper.getUserPermList(userId);
    }

    /**
     * 获取用户菜单列表
     * @param userId
     * @return
     */
    public List<MenuVO> getUserMenuList(Long userId) {
        List<MenuDTO> menuDTOList = this.menuMapper.getUserMenuList(userId);
        return getTreeList(0L, menuDTOList);
    }

    /**
     * 获取所有菜单列表
     * @return
     */
    public List<MenuVO> getMenuAllList(String status) {
        List<MenuDTO> menuDTOList = this.menuMapper.getMenuAllList(status);
        return getTreeList(0L, menuDTOList);
    }

    /**
     * * 解析树形数据
     * @param topId
     * @param menuDTOList
     * @return
     */
    public static List<MenuVO> getTreeList(Long topId, List<MenuDTO> menuDTOList) {
        List<MenuVO> menuVOList = Lists.newArrayList();
        // 获取顶层元素集合
        menuDTOList.forEach(menuDTO -> {
            MenuVO menuVO = new MenuVO();
            Long parentId = menuDTO.getParentId();
            if (topId.equals(parentId)) {
                BeanUtils.copyProperties(menuDTO, menuVO);
                List<MenuVO> childList = Lists.newArrayList();
                menuDTO.getChildList().forEach(menu -> {
                    MenuVO vo = new MenuVO();
                    BeanUtils.copyProperties(menu, vo);
                    childList.add(vo);
                });
                menuVO.setChildList(childList);
                menuVOList.add(menuVO);
            }
        });
        return menuVOList;
    }

    /**
     * 校验菜单名称
     * @param req
     * @return
     */
    public Boolean checkMenuname(MenuEditReq req) {
        Menu menu = new Menu();
        menu.setMenuName(req.getMenuName());
        return menuMapper.selectOne(menu)!=null?true:false;
    }

    /**
     * 菜单编辑
     * @param req
     * @param loginUser
     * @return
     */
    public int editMenu(MenuEditReq req, LoginUser loginUser) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(req,menu);
        menu.setUpdateTime(new Date());
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 菜单新增
     * @param req
     * @param loginUser
     * @return
     */
    public int addMenu(MenuEditReq req, LoginUser loginUser){
        Menu menu = new Menu();
        BeanUtils.copyProperties(req,menu);
        menu.setStatus(BuinessEnum.statusEnum.ENABLE.getCode());
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        return menuMapper.insertSelective(menu);
    }

}
