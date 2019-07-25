package com.linkedaim.admin.common.filter;

import com.linkedaim.admin.common.entity.LoginUser;

/**
 * @author zhaoyangyang
 * @title 绑定当前上下文用户
 * @date 2019-07-18
 */
public class UserContext implements AutoCloseable {

    static final ThreadLocal<LoginUser> current = new ThreadLocal<>();

    public UserContext(LoginUser user) {
        current.set(user);
    }

    public static LoginUser getCurrentUser() {
        return current.get();
    }

    @Override
    public void close() {
        current.remove();
    }
}
