package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dao.RedisDao;
import com.github.qingying0.im.dto.UserInfoDTO;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.entity.UserSession;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.mapper.UserSessionMapper;
import com.github.qingying0.im.service.IUserSessionService;
import com.github.qingying0.im.service.IUsersService;
import com.github.qingying0.im.utils.IdWorker;
import com.github.qingying0.im.utils.RedisKeyUtils;
import com.github.qingying0.im.utils.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
@Service
public class UserSessionServiceImpl extends ServiceImpl<UserSessionMapper, UserSession> implements IUserSessionService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserSessionMapper userSessionMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUsersService userService;

    @Autowired
    private RedisDao redisDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUserSession(Long user1Id, Long user2Id) {
        // 创建一个usersession 第一个是user1会话列表的session
        userSessionMapper.insert(createUserSession(user1Id, user2Id));
        // 添加user2会话列表的session
        userSessionMapper.insert(createUserSession(user2Id, user1Id));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserSession> getUserSession() {
        return userSessionMapper.selectByOwnerId(hostHolder.getUser().getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserUnreadnumZero(Long sessionId) {
        userSessionMapper.updateUnreadnumZeroBySessionIdAndUserId(sessionId, hostHolder.getUser().getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateNickname(String nickname) {
        userSessionMapper.updateNickname(hostHolder.getUser().getId(), nickname);
    }

    /**
     * 接受到message后更新session
     * @param message
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateSessionBySendMessage(Message message) {
        String content = "";
        if(message.getType().equals(0)) {
            content = message.getContent();
        } else if(message.getType().equals(1)) {
            content = "图片";
        } else if(message.getType().equals(2)) {
            content = "语音";
        }
        userSessionMapper.updateBySendMessage(message);
    }

    /**
     * 根据两个用户id创建usersession
     * @param ownerId
     * @param targetId
     * @return
     */
    public UserSession createUserSession(Long ownerId, Long targetId) {
        UserSession userSession = new UserSession();
        userSession.setId(idWorker.nextId());
        userSession.setOwnerId(ownerId);
        userSession.setTargetId(targetId);
        Users user = userService.getById(userSession.getTargetId());
        userSession.setNickname(user.getUsername());
        userSession.setStatus(SystemConst.SESSION_ON);
        userSession.setUnreadNum(0);
        userSession.setIsMuted(false);
        userSession.setSessionId(idWorker.nextId());
        userSession.setSessionType(SystemConst.SESSION_TYPE_USER);
        userSession.setContent("你们已经成为好友，现在可以开始聊天");
        return userSession;
    }

    @Override
    public void updateSessionByReceivedMessage(Message message) {
        if (redisDao.sHasKey(RedisKeyUtils.getOnlineKey(), message.getTargetId())) {
           userSessionMapper.updateByReceivedMessage(message);
        } else {
            userSessionMapper.updateContent(message);
        }
    }
}
