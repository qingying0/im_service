package com.github.qingying0.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qingying0.im.dto.UserDTO;
import com.github.qingying0.im.dto.UserInfoDTO;
import com.github.qingying0.im.entity.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
public interface IUsersService extends IService<Users> {

    UserDTO login(String phone, String password);

    void register(Users user);

    UserInfoDTO getUserById(Long id);

    List<UserDTO> search(String search);

}
