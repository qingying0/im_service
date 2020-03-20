package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.entity.UserSession;
import com.github.qingying0.im.service.IUserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private IUserSessionService userSessionService;

    @GetMapping
    public ResultDTO getSession() {
        return ResultDTO.okOf(userSessionService.getUserSession());
    }

    @PostMapping("/open")
    public ResultDTO openSession(Long sessionId) {
        userSessionService.updateUserUnreadnumZero(sessionId);
        return ResultDTO.okOf();
    }
}
