package com.github.qingying0.im.netty.customhandler;

import com.alibaba.fastjson.JSON;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.dto.MsgDTO;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.enums.SendMsgTypeEnum;
import com.github.qingying0.im.netty.UserChannelRelation;
import com.github.qingying0.im.service.IMessageService;
import io.netty.channel.Channel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Component
public class SendMessageHandler {

    @Autowired
    private IMessageService messageService;

    private ExecutorService executorService = Executors.newFixedThreadPool(50);

    public void sendMessage(MessageDTO messageDTO) {
        Channel channel = UserChannelRelation.getChannel(messageDTO.getTargetId());
        if(channel != null) {
            sendMsg(channel, messageDTO);
            return ;
        }
        Message message = new Message();
        BeanUtils.copyProperties(messageDTO, message);
        messageService.save(message);
    }

    public void sendMsg(Channel channel, MessageDTO messageDTO) {
        MsgDTO msgDTO = new MsgDTO();
        msgDTO.setType(SendMsgTypeEnum.MESSAGE.getType());
        msgDTO.setData(messageDTO);
        System.out.println("发送" + JSON.toJSONString(msgDTO));
        channel.writeAndFlush(JSON.toJSONString(msgDTO));
        addToAckMap(channel, messageDTO);
    }

    private void addToAckMap(Channel channel, MessageDTO messageDTO) {
        channel.attr(OnlineHandler.NON_ACKED_MAP).get().put(messageDTO.getId(), messageDTO);
        executorService.execute(() -> {
            if(channel.isActive()) {
                checkAndResend(channel, messageDTO);
            }
        });
    }

    private void checkAndResend(Channel channel, MessageDTO messageDTO) {
        Long messageId = messageDTO.getId();
        int tryTime = 2;
        Map map = channel.attr(OnlineHandler.NON_ACKED_MAP).get();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(tryTime > 0) {
            if (channel.attr(OnlineHandler.NON_ACKED_MAP).get().containsKey(messageId)) {
                sendMsg(channel, messageDTO);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
            tryTime--;
        }
    }
}
