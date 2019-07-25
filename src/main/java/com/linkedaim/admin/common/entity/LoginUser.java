package com.linkedaim.admin.common.entity;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 登录绑定当前用户
 * @date 2019-07-18
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户主键ID
     */
    public Long userId;

    /**
     * 账号
     */
    public String account;

    public LoginUser() {
    }

    public LoginUser(String account) {
        this.account = account;
    }

    public LoginUser(Long userId) {
        this.userId = userId;
    }

    public LoginUser(Long userId, String account) {
        this.userId = userId;
        this.account = account;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
