package com.github.qingying0.im.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String IM_MESSAGE = "im-message";

    public static final String IM_GROUP_MESSAGE = "im-group-message";

    public void send(String queue, String message) {
        rabbitTemplate.convertAndSend(queue, message);
    }
}
