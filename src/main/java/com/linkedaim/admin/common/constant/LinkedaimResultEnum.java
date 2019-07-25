package com.linkedaim.admin.common.constant;

/**
 * @author zhaoyangyang
 * @title 系统响枚举
 * @date 2019-07-18
 */

public enum LinkedaimResultEnum implements ResponseCode {
    /*后台登录枚举*/
    LOGIN_FAIL("100005", "账号或密码错误"),
    ACCOUNT_FAIL("100006", "账号已被锁定,请联系管理员"),
    AUTH_FAIL("100007", "认证失败"),
    NOT_LOGIN_FAIL("100008", "账号未登录"),
    NO_AUTH("100009", "无权限，请联系管理员"),
    NOUSER_FAIL("100010", "账号不存在"),
    OLDPASSWD_FAIL("100011", "旧密码错误"),
    USER_NOTEXIST("100012", "账号不存在，请联系管理员"),
    /*菜单模块*/
    MENU_FAIL("110000", "获取菜单失败"),
    /*用户模块*/
    USER_FAIL("120000", "获取用户失败，请联系管理员"),
    /*角色模块*/
    ROLE_FAIL("130000", "获取角色信息失败，请联系管理员"),

    ;

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private LinkedaimResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
