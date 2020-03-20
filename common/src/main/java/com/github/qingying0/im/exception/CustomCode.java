package com.github.qingying0.im.exception;

public enum CustomCode implements ICustomCode {

    SUCCESS(200, "成功"),
    FAIL(2000, "出现未知错误"),
    ;

    private String message;
    private Integer code;

    CustomCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
