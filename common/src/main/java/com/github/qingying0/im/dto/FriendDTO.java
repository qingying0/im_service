package com.github.qingying0.im.dto;


import lombok.Data;

import java.util.Date;

@Data
public class FriendDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    private Date createTime;
    private String description;
    private Boolean sex;
    private Integer status;
}
