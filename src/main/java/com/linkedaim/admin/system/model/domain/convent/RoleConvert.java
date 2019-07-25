package com.linkedaim.admin.system.model.domain.convent;

import com.google.common.collect.Lists;
import com.linkedaim.admin.system.model.domain.response.RoleVO;
import com.linkedaim.admin.system.model.domain.response.UserVO;
import com.linkedaim.admin.system.model.entity.Role;
import com.linkedaim.admin.system.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoyangyang
 * @title 角色对象装换
 * @date 2019-07-02
 */

public class RoleConvert implements Serializable {

    /**
     * 角色对象装换
     * @param role
     * @return
     */
    public static RoleVO roleToRoleVO(Role role) {
        RoleVO roleVO = new RoleVO();
        if (role != null) {
            BeanUtils.copyProperties(role, roleVO);
        }
        return roleVO;
    }

    /**
     * 角色集合装换
     * @param userList
     * @return
     */
    public static List<RoleVO> roleToRoleVOList(List<Role> roleList) {
        List<RoleVO> roleVOList = Lists.newLinkedList();
        roleList.forEach(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            roleVOList.add(roleVO);
        });
        return roleVOList;
    }
}
