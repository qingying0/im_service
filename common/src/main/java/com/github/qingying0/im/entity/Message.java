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
public class Message implements Serializable {

    private Long id;

    private String content;

    private Date createTime;

    private Long sendId;

    private Long targetId;

    private Integer type;

    private Integer status;

    private Long sessionId;


}
