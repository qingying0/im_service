package com.github.qingying0.im.enums;

public enum MesssageTypeEnum {

    TEXT(0, "文本数据"),
    PICTURE(1, "图片数据"),
    VOICE(2, "语音数据"),
    ;

    public final Integer type;
    public final String msg;

    MesssageTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
