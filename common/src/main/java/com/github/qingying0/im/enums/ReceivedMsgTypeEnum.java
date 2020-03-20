package com.github.qingying0.im.enums;



public enum ReceivedMsgTypeEnum {
    HEART(0),
    ONLINE(1),
    ACK_MESSAGE(2),
    OFFLINE(3),
    ;

    int type;

    ReceivedMsgTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ReceivedMsgTypeEnum getByType(int type) {
        for(ReceivedMsgTypeEnum msg : values()) {
            if(msg.getType() == type) {
                return msg;
            }
        }
        return null;
    }
}
