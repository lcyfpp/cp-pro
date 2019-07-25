package com.linkedaim.admin.common.constant;

/**
 * @author zhaoyangyang
 * @title 结果集枚举
 * @date 2019-07-18
 */

public enum ResultEnum implements ResponseCode {

    SUCCESS("100000", "执行成功!"),
    CALL_FAIL("100001", "服务调用失败"),
    ERROR_ARGUMENT("100002", "参数错误"),
    ACCESS_TIMEOUT("100003", "访问超时"),
    CODE_FAILURE("100004", "验证码失效"),
    SERVICE_INTERN_FAILURE("100005", "服务内部错误");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
