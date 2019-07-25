package com.linkedaim.admin.system.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_user")
public class User implements Serializable {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号：唯一标识
     */
    private String account;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    private String status;

    /**
     * 性别 0男 1女
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 最近访问时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

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
     * 获取用户id
     * @return id - 用户id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置用户id
     * @param id 用户id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取账号：唯一标识
     * @return account - 账号：唯一标识
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号：唯一标识
     * @param account 账号：唯一标识
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取邮箱
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取手机号码
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取状态 0锁定 1有效
     * @return status - 状态 0锁定 1有效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 0锁定 1有效
     * @param status 状态 0锁定 1有效
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取性别 0男 1女
     * @return sex - 性别 0男 1女
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别 0男 1女
     * @param sex 性别 0男 1女
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 获取头像
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取最近访问时间
     * @return last_login_time - 最近访问时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近访问时间
     * @param lastLoginTime 最近访问时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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
