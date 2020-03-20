package com.github.qingying0.im.enums;

public enum  MessageStatusEnum {

    SEND(0, "已经发送"),
    RECEIVED(1, "已经接受"),
    READ(2, "已经阅读")
    ;
    Integer status;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    String msg;

    MessageStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
