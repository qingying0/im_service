package com.github.qingying0.im.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {
    private Long id;

    private String username;

    private String phone;

    private Date createTime;

    private Date updateTime;

    private String description;

    private Integer status;

    private String avatarUrl;

    private boolean friend;
}
