package com.github.qingying0.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qingying0.im.entity.GroupChat;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingying0
 * @since 2020-03-21
 */
public interface IGroupService extends IService<GroupChat> {

    GroupChat saveGroup();

    void addGroup(Long userId, Long groupId, String username, String groupName);

    List<Long> getUserIdByGroupId(Long groupId);

    GroupChat getById(Long groupId);

    List<GroupChat> getGroup();
}
