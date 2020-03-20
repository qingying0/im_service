package com.github.qingying0.im.rabbit;

import com.alibaba.fastjson.JSON;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.netty.customhandler.SendMessageHandler;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "im-message")
public class RabbitMQListener {

    @Autowired
    private SendMessageHandler sendMessageHandler;

    @RabbitHandler
    public void messageHandler(String jsonMessage) {
        MessageDTO messageDTO = JSON.parseObject(jsonMessage, MessageDTO.class);
        System.out.println(messageDTO);
        sendMessageHandler.sendMessage(messageDTO);
    }
}
