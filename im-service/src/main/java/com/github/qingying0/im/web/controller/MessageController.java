package com.github.qingying0.im.web.controller;

import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.dto.ResultDTO;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private ISendMessageService messageService;

    @PostMapping
    public ResultDTO sendMessage(Message message) {
        MessageDTO sendMessage = messageService.sendMessage(message);
        return ResultDTO.okOf(sendMessage);
    }
}
