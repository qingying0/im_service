package com.github.qingying0.im.enums;

public enum  SendMsgTypeEnum {
    HEART_ACK(0),
    ONLINE_ACK(1),
    MESSAGE(2),
    ;
    int type;

    SendMsgTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
