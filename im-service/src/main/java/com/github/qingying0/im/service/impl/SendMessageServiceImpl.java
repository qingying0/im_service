package com.github.qingying0.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.enums.MessageStatusEnum;
import com.github.qingying0.im.rabbitmq.RabbitMQSender;
import com.github.qingying0.im.service.IGroupService;
import com.github.qingying0.im.service.ISendMessageService;
import com.github.qingying0.im.service.IUserSessionService;
import com.github.qingying0.im.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SendMessageServiceImpl implements ISendMessageService {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RabbitMQSender sender;

    @Autowired
    private IUserSessionService userSessionService;

    @Autowired
    private IGroupService groupService;

    @Override
    public MessageDTO sendMessage(Message message) {
        message.setCreateTime(new Date());
        MessageDTO messageDTO = getMessageDTO(message);
        sender.send(RabbitMQSender.IM_MESSAGE, JSON.toJSONString(messageDTO));
        userSessionService.updateSessionBySendMessage(message);
        userSessionService.updateSessionByReceivedMessage(message);
        return messageDTO;
    }

    @Override
    public MessageDTO sendGroupMessage(Message message) {
        message.setCreateTime(new Date());
        MessageDTO messageDTO = getMessageDTO(message);
        sender.send(RabbitMQSender.IM_GROUP_MESSAGE, JSON.toJSONString(messageDTO));
        return messageDTO;
    }

    public MessageDTO getMessageDTO(Message message) {
        message.setSendId(hostHolder.getUser().getId());
        message.setStatus(MessageStatusEnum.SEND.getStatus());
        message.setId(idWorker.nextId());
        MessageDTO messageDTO = new MessageDTO();
        BeanUtils.copyProperties(message, messageDTO);
        messageDTO.setAvatarUrl(hostHolder.getUser().getAvatarUrl());
        messageDTO.setUsername(hostHolder.getUser().getUsername());
        return messageDTO;
    }
}
