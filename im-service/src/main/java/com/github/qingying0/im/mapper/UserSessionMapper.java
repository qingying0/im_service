package com.github.qingying0.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.entity.UserSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
@Repository
public interface UserSessionMapper extends BaseMapper<UserSession> {

    void updateUnreadnumZeroBySessionIdAndUserId(@Param("sessionId") Long sessionId, @Param("userId") Long userId);

    void updateNickname(@Param("targetId") Long targetId, @Param("nickName") String nickname);

    void updateBySendMessage(@Param("message") Message message);

    void updateUnreadNumAdd(@Param("sessionId")Long sessionId, @Param("ownerId")Long userId);

    void updateByReceivedMessage(@Param("message") Message message);

    List<UserSession> selectByOwnerId(Long userId);

    void updateContent(@Param("message")Message message);
}
