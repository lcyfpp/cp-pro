package com.linkedaim.admin.common.constant;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zhaoyangyang
 * @title 响应实体对象
 * @date 2019-07-18
 */

public class ResultResponse<T> implements Serializable {

    @ApiModelProperty(value = "状态结果")
    private boolean success;

    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public static ResultResponse ok() {
        return ok((ResponseCode) ResultEnum.SUCCESS);
    }

    public static ResultResponse ok(ResponseCode responseCode) {
        return of(true, responseCode.getCode(), responseCode.getMessage(), (Object) null);
    }

    public static ResultResponse ok(final String responseCode, final String responseMsg) {
        return ok(new ResponseCode() {
            public String getCode() {
                return responseCode;
            }

            public String getMessage() {
                return responseMsg;
            }
        });
    }

    public static <T> ResultResponse ok(T data) {
        return ok((ResponseCode) ResultEnum.SUCCESS, (Object) data);
    }

    public static <T> ResultResponse ok(ResponseCode responseCode, T data) {
        return of(true, responseCode.getCode(), responseCode.getMessage(), data);
    }

    public static ResultResponse fail(ResponseCode responseCode) {
        return of(false, responseCode.getCode(), responseCode.getMessage(), (Object) null);
    }

    public static ResultResponse fail(final String responseCode, final String responseMsg) {
        return fail(new ResponseCode() {
            public String getCode() {
                return responseCode;
            }

            public String getMessage() {
                return responseMsg;
            }
        });
    }

    public static <T> ResultResponse fail(ResponseCode responseCode, T data) {
        return of(false, responseCode.getCode(), responseCode.getMessage(), data);
    }

    private static <T> ResultResponse of(boolean success, String code, String message, T data) {
        return builder().success(success).code(code).message(message).data(data).build();
    }

    public static <T> ResultResponse.ResultResponseBuilder<T> builder() {
        return new ResultResponse.ResultResponseBuilder();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public ResultResponse() {
    }

    public ResultResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static class ResultResponseBuilder<T> {
        private boolean success;
        private String code;
        private String message;
        private T data;

        ResultResponseBuilder() {
        }

        public ResultResponse.ResultResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ResultResponse.ResultResponseBuilder<T> code(String code) {
            this.code = code;
            return this;
        }

        public ResultResponse.ResultResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResultResponse.ResultResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResultResponse<T> build() {
            return new ResultResponse(this.success, this.code, this.message, this.data);
        }

        public String toString() {
            return "ResultResponse.ResultResponseBuilder(success=" + this.success + ", code=" + this.code + ", message=" + this.message + ", data=" + this.data + ")";
        }
    }
}
