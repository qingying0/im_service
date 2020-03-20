package com.github.qingying0.im.netty.handler;

import com.github.qingying0.im.netty.UserChannelRelation;
import com.github.qingying0.im.netty.customhandler.OnlineHandler;
import com.github.qingying0.im.utils.SpringUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

@Component
public class StateHandlerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    Long userId = UserChannelRelation.getUserId(ctx.channel());
                    // TODO 删除map里面的channel
                    System.out.println("un bind userId = " + userId);
                    OnlineHandler onlineHandler = SpringUtils.getBean(OnlineHandler.class);
                    onlineHandler.offline(ctx.channel());
                    ctx.channel().closeFuture();
                    break;
                default:
                    break;
            }
        }
    }
}
