package com.github.qingying0.im.dto;

import lombok.Data;

@Data
public class RequestDTO {
    Long id;
    String avatarUrl;
    String username;
    Integer status;
    Integer type;
    String content;
}
