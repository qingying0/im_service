package com.github.qingying0.im.service;

import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.entity.Message;

public interface ISendMessageService {
    MessageDTO sendMessage(Message message);

    MessageDTO sendGroupMessage(Message message);
}
