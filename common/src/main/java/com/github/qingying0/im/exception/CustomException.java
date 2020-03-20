package com.github.qingying0.im.exception;

public class CustomException extends RuntimeException {

    private String message;
    private Integer code;

    public CustomException(ICustomCode customErrorCode) {
        this.message = customErrorCode.getMessage();
        this.code = customErrorCode.getCode();
    }

    public CustomException(String message) {
        this.message = message;
        this.code = 500;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
