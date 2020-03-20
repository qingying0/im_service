package com.github.qingying0.im.netty;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class UserChannelRelation {

    private static final ConcurrentHashMap<Long, Channel> userChannel = new ConcurrentHashMap<>(15000);
    private static final ConcurrentHashMap<Channel, Long> channelUser = new ConcurrentHashMap<>(15000);

    public static void setUserChannel(Long userId, Channel channel) {
        userChannel.put(userId, channel);
    }

    public static void removeUserChannel(Long userId) {
        userChannel.remove(userId);
    }

    public static Channel getChannel(Long userId) {
        return userChannel.get(userId);
    }

    public static void setChannelUserId(Channel channel, Long userId) {
        channelUser.put(channel, userId);
    }

    public static void removeChannelUserId(Channel channel) {
        channelUser.remove(channel);
    }

    public static Long getUserId(Channel channel) {
        return channelUser.get(channel);
    }

    public static int getSize() {
        return userChannel.size();
    }
}
