package com.linkedaim.admin.common.exception;

/**
 * 自定义异常(CustomException)
 * @author zhaoyangyang
 * @date 2019-07-18
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException() {
        super();
    }
}
