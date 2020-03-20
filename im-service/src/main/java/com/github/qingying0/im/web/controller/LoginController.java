package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.dto.UserDTO;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.exception.CustomException;
import com.github.qingying0.im.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @Autowired

    private IUsersService usersService;

    @GetMapping("/login")
    public ResultDTO login(String phone, String password) {
        UserDTO userDTO = usersService.login(phone, password);
        return ResultDTO.okOf(userDTO);
    }

    @PostMapping("/register")
    public ResultDTO login(@Validated Users user, BindingResult result) {
        if(result.hasErrors()) {
            for(ObjectError error : result.getAllErrors()) {
                throw new CustomException(error.getDefaultMessage());
            }
        }
        usersService.register(user);
        return ResultDTO.okOf();
    }
}
