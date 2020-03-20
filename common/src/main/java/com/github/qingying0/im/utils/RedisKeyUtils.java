package com.github.qingying0.im.utils;

/**
 * 生成redisKey的工具类
 */
public class RedisKeyUtils {

    public static final String SPLIT= ":";
    public static final String TOKEN = "token";
    public static final String TARGET_REQUEST = "targetRequest";
    public static final String SEND_REQUEST = "sendRequest";
    public static final String ONLINE = "online";
    public static final String KEY = "key";


    public static String getTokenKey(String token) {
        return TOKEN + SPLIT + token;
    }

    public static String getTargetRequestKey(Long userId)  {
        return TARGET_REQUEST + SPLIT + userId;
    }

    public static String getSendRequestKey(Long userId)  {
        return SEND_REQUEST + SPLIT + userId;
    }

    public static String getOnlineKey() {
        return ONLINE + SPLIT + KEY;
    }
}
