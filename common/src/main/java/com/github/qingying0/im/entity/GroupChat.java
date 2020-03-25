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
 * @since 2020-03-21
 */
@Data
public class GroupChat {

    private Long id;

    private String groupName;

    private String description;

    private Long ownerId;

    private String avatarUrl;

    private Date createTime;

}
