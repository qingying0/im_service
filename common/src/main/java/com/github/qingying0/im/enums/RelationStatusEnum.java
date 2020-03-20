package com.github.qingying0.im.enums;

public enum  RelationStatusEnum {

    FRIEND(0, "好友"),
    DELETE(1, "删除好友"),
    SHIELDING(2, "屏蔽"),
    ;

    Integer status;
    String msg;

    RelationStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
