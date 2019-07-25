package com.linkedaim.admin.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.linkedaim.admin.common.entity.LoginUser;
import com.linkedaim.admin.common.entity.PageResBean;
import com.linkedaim.admin.common.service.impl.BaseServiceImpl;
import com.linkedaim.admin.system.constants.BuinessEnum;
import com.linkedaim.admin.system.mapper.RoleMapper;
import com.linkedaim.admin.system.mapper.RoleMenuMapper;
import com.linkedaim.admin.system.model.domain.convent.RoleConvert;
import com.linkedaim.admin.system.model.domain.request.AuthorizeMenuReq;
import com.linkedaim.admin.system.model.domain.request.RoleEditReq;
import com.linkedaim.admin.system.model.domain.request.RoleListReq;
import com.linkedaim.admin.system.model.domain.response.RoleVO;
import com.linkedaim.admin.system.model.entity.Role;
import com.linkedaim.admin.system.model.entity.RoleMenu;
import com.linkedaim.admin.system.model.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleService extends BaseServiceImpl<Role> {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 角色列表查询
     * @param req
     * @param userId
     * @return
     */
    public PageResBean<RoleVO> getPageRoleList(RoleListReq req, Long userId) {
        PageResBean<RoleVO> pageResBean = new PageResBean<>();
        PageHelper.startPage(req.getPageNo(),req.getPageSize());
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(req.getStatus())) {
            criteria.andEqualTo("status", req.getStatus());
        }
        if (StringUtils.isNotEmpty(req.getRoleCode())) {
            criteria.andLike("role_code", req.getRoleCode());
        }
        if (StringUtils.isNotEmpty(req.getRemark())) {
            criteria.andLike("remark", req.getRemark());
        }
        if (StringUtils.isNotEmpty(req.getRoleName())) {
            criteria.andLike("role_nmae", req.getRoleName());
        }
        if (req.getId()!=null) {
            criteria.andEqualTo("id", req.getId());
        }
        example.setOrderByClause("id desc");
        List<Role> roleList = roleMapper.selectByExample(example);
        PageInfo<Role> pageInfo = new PageInfo(roleList);
        List<RoleVO> roleVOList = RoleConvert.roleToRoleVOList(roleList);
        pageResBean.setPage(req.getPageNo());
        pageResBean.setPageSize(req.getPageSize());
        pageResBean.setTotal((int)pageInfo.getTotal());
        pageResBean.setList(roleVOList);
        return pageResBean;
    }

    /**
     * 获取可用角色列表
     * @param userId
     * @return
     */
    public List<RoleVO> getRoleList(Long userId) {
        List<RoleVO> roleVOList = Lists.newArrayList();
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", BuinessEnum.statusEnum.ENABLE.getCode());
        example.setOrderByClause("id desc");
        List<Role> roleList = roleMapper.selectByExample(example);
        roleVOList = RoleConvert.roleToRoleVOList(roleList);
        return roleVOList;
    }


    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    public List<Role> getUserRoleList(Long userId) {
        return this.roleMapper.getUserRoleList(userId);
    }

    /**
     * 校验角色名称
     * @param req
     * @return
     */
    public Boolean checkRolename(RoleEditReq req) {
        Role role = new Role();
        role.setRoleName(req.getRoleName());
        return roleMapper.selectOne(role)!=null?true:false;
    }

    /**
     * 角色编辑
     * @param req
     * @param loginUser
     * @return
     */
    public int editRole(RoleEditReq req, LoginUser loginUser) {
        Role role = new Role();
        BeanUtils.copyProperties(req,role);
        role.setUpdateTime(new Date());
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 角色新增
     * @param req
     * @param loginUser
     * @return
     */
    public int addRole(RoleEditReq req, LoginUser loginUser){
        Role role = new Role();
        BeanUtils.copyProperties(req,role);
        role.setStatus(BuinessEnum.statusEnum.ENABLE.getCode());
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        return roleMapper.insertSelective(role);
    }

    /**
     * 角色授权菜单
     * @param req
     * @param loginUser
     * @return
     */
    public int authorizeMenu(AuthorizeMenuReq req, LoginUser loginUser){
        int result = 0;
        List<Long> menuIds = req.getMenuIds();
        if (!CollectionUtils.isEmpty(menuIds)){
            //删除旧角色
            Example example = new Example(UserRole.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("role_id", req.getRoleId());
            roleMenuMapper.deleteByExample(example);
            List<RoleMenu> roleMenuList = Lists.newLinkedList();
            menuIds.forEach(aLong -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(req.getRoleId());
                roleMenu.setMenuId(aLong);
                roleMenu.setCreateTime(new Date());
                roleMenu.setUpdateTime(new Date());
                roleMenuList.add(roleMenu);
            });
            result = roleMenuMapper.insertList(roleMenuList);
        }
        return result;
    }
}
