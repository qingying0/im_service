package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.dto.UserDTO;
import com.github.qingying0.im.dto.UserInfoDTO;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUsersService usersService;

    @PutMapping
    public ResultDTO updateUser(Users users) {
        usersService.updateById(users);
        return ResultDTO.okOf();
    }

    @GetMapping("/search")
    public ResultDTO searchUser(String search) {
        List<UserDTO> listUser = usersService.search(search);
        return ResultDTO.okOf(listUser);
    }
}
