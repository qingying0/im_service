package com.github.qingying0.im.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class Users implements Serializable {

    private Long id;

    @NotEmpty(message = "用户名不能为空")
    @Size(min = 3, message = "用户名长度太短")
    private String username;

    @JsonIgnore
    @NotEmpty(message = "密码不能为空")
    @Size(min = 3, message = "密码长度太短")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号长度错误，请填写11位手机号")
    private String phone;

    private Date createTime;

    private Date updateTime;

    private String description;

    private String qrcode;

    @JsonIgnore
    private Integer status;

    private String avatarUrl;

    @JsonIgnore
    private String salt;

    private Boolean sex;
}
