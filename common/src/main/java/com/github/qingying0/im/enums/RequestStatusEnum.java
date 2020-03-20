package com.github.qingying0.im.enums;

public enum RequestStatusEnum {

    SEND(0, "已发送"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败"),
    ;


    public final Integer status;
    public final String msg;

    RequestStatusEnum(Integer status,String msg) {
        this.status = status;
        this.msg = msg;
    }
}
