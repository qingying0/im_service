package com.github.qingying0.im.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserSession implements Serializable {

    private Long id;

    private Long ownerId;

    private Long targetId;

    private Long sessionId;

    private String nickname;

    private Date createTime;

    private Date updateTime;

    private Integer unreadNum;

    private Integer status;

    private Boolean isMuted;

    private String content;

    private Integer sessionType;


}
