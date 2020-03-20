package com.github.qingying0.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qingying0.im.dto.FriendDTO;
import com.github.qingying0.im.entity.Relation;
import com.github.qingying0.im.entity.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
public interface IRelationService extends IService<Relation> {

    List<FriendDTO> getFriends();

    Relation getByUserAndFriend(Long userId, Long targetId);
}
