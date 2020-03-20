package com.github.qingying0.im.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Long id;

    private String content;

    private Date createTime;

    private Long sendId;

    private Long targetId;

    private Integer type;

    private Integer status;

    private Long sessionId;

    private String username;

    private String avatarUrl;

}
