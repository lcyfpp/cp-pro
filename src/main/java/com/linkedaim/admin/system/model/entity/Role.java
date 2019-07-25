package com.linkedaim.admin.system.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_role")
public class Role implements Serializable {
    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 状态 1-启用 0-禁用
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取角色id
     * @return id - 角色id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置角色id
     * @param id 角色id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取角色编码
     * @return role_code - 角色编码
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * 设置角色编码
     * @param roleCode 角色编码
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /**
     * 获取角色描述
     * @return remark - 角色描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置角色描述
     * @param remark 角色描述
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取状态 1-启用 0-禁用
     * @return status - 状态 1-启用 0-禁用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 1-启用 0-禁用
     * @param status 状态 1-启用 0-禁用
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取创建时间
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
