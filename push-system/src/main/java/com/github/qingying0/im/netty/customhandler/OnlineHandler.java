package com.github.qingying0.im.netty.customhandler;

import com.alibaba.fastjson.JSONObject;
import com.github.qingying0.im.dao.RedisDao;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.netty.UserChannelRelation;
import com.github.qingying0.im.service.IMessageService;
import com.github.qingying0.im.service.IUsersService;
import com.github.qingying0.im.utils.RedisKeyUtils;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineHandler {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private SendMessageHandler sendMessageHandler;
    public static final AttributeKey<ConcurrentHashMap> NON_ACKED_MAP = AttributeKey.valueOf("non_acked_map");

    // 将在线的用户和对应的channel保存到map
    public void online(Channel channel, String token) {
        channel.attr(NON_ACKED_MAP).set(new ConcurrentHashMap<Long, JSONObject>());
        Users user = (Users)redisDao.get(RedisKeyUtils.getTokenKey(token));
        UserChannelRelation.setChannelUserId(channel, user.getId());
        UserChannelRelation.setUserChannel( user.getId(), channel);
        redisDao.sSet(RedisKeyUtils.getOnlineKey(),  user.getId());
        pushUnreadMessage(channel, user);

    }

    public void offline(Channel channel) {
        Long userId = UserChannelRelation.getUserId(channel);
        if(userId != null ) {
            UserChannelRelation.removeChannelUserId(channel);
            UserChannelRelation.removeUserChannel(userId);
            redisDao.setRemove(RedisKeyUtils.getOnlineKey(), userId);
        }
    }

    public void pushUnreadMessage(Channel channel, Users user) {
        List<MessageDTO> listMessage = messageService.getByTargetId(user.getId());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(MessageDTO messageDTO : listMessage) {
            sendMessageHandler.sendMsg(channel, messageDTO);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
