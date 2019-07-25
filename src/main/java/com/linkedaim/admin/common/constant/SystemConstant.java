package com.linkedaim.admin.common.constant;

/**
 * 系统公共常量
 * @author zhaoyangyang
 * @date 2019-07-18
 */
public class SystemConstant {

    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * request请求头属性
     */
    public static final String REQUEST_AUTH_HEADER = "Authorization";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-userId:
     */
    public final static String USERID = "userId";

    /**
     * 默认初始密码
     */
    public final static String DEFAULT_PASSWORD = "123456";

    /**
     * 默认角色
     */
    public static final String DEFALT_ROLE_CODE = "user";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";

    /**
     * redis-key-前缀-user:login:
     */
    public final static String PREFIX_USER_LOGIN = "user:login:";


}
