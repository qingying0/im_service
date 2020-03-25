package com.github.qingying0.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.entity.UserSession;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
public interface IUserSessionService extends IService<UserSession> {

    void saveUserSession(Long user1Id, Long user2Id);

    void saveUserGroupSession(Long userId, Long groupId, String username, String groupName);

    List<UserSession> getUserSession();

    void updateUserUnreadnumZero(Long sessionId);

    void updateNickname(String nickname);

    void updateSessionBySendMessage(Message message);

    void updateSessionByReceivedMessage(Message message);
}
