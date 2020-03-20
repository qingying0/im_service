package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
