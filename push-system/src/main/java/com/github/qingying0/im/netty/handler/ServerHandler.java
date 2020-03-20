package com.github.qingying0.im.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qingying0.im.enums.ReceivedMsgTypeEnum;
import com.github.qingying0.im.enums.SendMsgTypeEnum;
import com.github.qingying0.im.netty.customhandler.OnlineHandler;
import com.github.qingying0.im.service.IMessageService;
import com.github.qingying0.im.service.IUsersService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ChannelHandler.Sharable
@Component
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Autowired
    private OnlineHandler onlineHandler;

    @Autowired
    private IMessageService messageService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("msg = " + msg);
        String[] split = msg.split("\n");
        for(String s : split) {
            try {
                JSONObject object = JSON.parseObject(msg);
                handlerMsg(ctx, object);
            } catch (JSONException e) {
                return;
            }
        }
    }

    public void handlerMsg(ChannelHandlerContext ctx, JSONObject object) {
        ReceivedMsgTypeEnum type;
        try {
            type = ReceivedMsgTypeEnum.getByType(object.getInteger("type"));
        } catch (NullPointerException e) {
            return;
        }
        switch (type) {
            case HEART:
                Map<String, Integer> heartMap = new HashMap<>();
                heartMap.put("type", SendMsgTypeEnum.HEART_ACK.getType());
                ctx.writeAndFlush(JSON.toJSONString(heartMap));
                break;
            case ONLINE:
                Map<String, Integer> onlineMap = new HashMap<>();
                onlineMap.put("type", SendMsgTypeEnum.ONLINE_ACK.getType());
                ctx.writeAndFlush(JSON.toJSONString(onlineMap));
                onlineHandler.online(ctx.channel(), object.getString("token"));
                break;
            case ACK_MESSAGE:
                // TODO
                Long id = object.getLong("id");
                System.out.println("message = " + messageService.getById(id));
                ctx.channel().attr(OnlineHandler.NON_ACKED_MAP).get().remove(id);
                messageService.removeById(id);
                break;
            case OFFLINE:
                onlineHandler.offline(ctx.channel());
                ctx.channel().close();
                break;
            default:
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception");
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "un active");
        onlineHandler.offline(ctx.channel());
        super.channelInactive(ctx);
    }
}
