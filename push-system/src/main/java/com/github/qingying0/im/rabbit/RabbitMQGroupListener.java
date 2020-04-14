package com.github.qingying0.im.rabbit;

import com.alibaba.fastjson.JSON;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.netty.customhandler.SendMessageHandler;
import com.github.qingying0.im.service.IGroupService;
import com.github.qingying0.im.service.IUserSessionService;
import com.github.qingying0.im.utils.IdWorker;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: qingying0
 * @since: 9:01 2020/4/14
 * @description:
 */

@Component
@RabbitListener(queues = "im-group-message")
public class RabbitMQGroupListener {

    @Autowired
    private SendMessageHandler sendMessageHandler;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IUserSessionService userSessionService;

    @RabbitHandler
    public void messageHandler(String jsonMessage) {
        MessageDTO messageDTO = JSON.parseObject(jsonMessage, MessageDTO.class);
        Long groupId = messageDTO.getTargetId();
        List<Long> groupUserId = groupService.getUserIdByGroupId(groupId);
        for(Long userId : groupUserId) {
            if(userId.equals(messageDTO.getSendId())){
                continue;
            }
            messageDTO.setId(idWorker.nextId());
            messageDTO.setTargetId(userId);
            sendMessageHandler.sendMessage(messageDTO);
        }
        System.out.println(messageDTO);
    }

}
