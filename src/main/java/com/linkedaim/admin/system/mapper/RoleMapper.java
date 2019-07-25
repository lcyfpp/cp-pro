package com.linkedaim.admin.system.mapper;

import com.linkedaim.admin.common.mapper.BaseMapper;
import com.linkedaim.admin.system.model.entity.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<Role> getUserRoleList(Long userId);

}
