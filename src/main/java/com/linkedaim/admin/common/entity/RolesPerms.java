package com.linkedaim.admin.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author zhaoyangyang
 * @title 角色权限信息
 * @date 2019-07-18
 */
@Data
public class RolesPerms implements Serializable {

    /**
     * 角色
     */
    private Set<String> roles;

    /**
     * 权限
     */
    private Set<String> stringPermissions;
}
