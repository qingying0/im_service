package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dto.UserInfoDTO;
import com.github.qingying0.im.entity.GroupChat;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.mapper.GroupMapper;
import com.github.qingying0.im.service.IGroupService;
import com.github.qingying0.im.service.IRelationService;
import com.github.qingying0.im.service.IUserSessionService;
import com.github.qingying0.im.service.IUsersService;
import com.github.qingying0.im.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qingying0
 * @since 2020-03-21
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupChat> implements IGroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUserSessionService userSessionService;

    @Autowired
    private IdWorker idWorker;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public GroupChat saveGroup() {
        GroupChat group = new GroupChat();
        group.setGroupName(hostHolder.getUser().getUsername() + "的群聊");
        group.setAvatarUrl(hostHolder.getUser().getAvatarUrl());
        group.setId(idWorker.nextId());
        group.setOwnerId(hostHolder.getUser().getId());
        group.setCreateTime(new Date());
        groupMapper.insert(group);
        addGroup(group.getOwnerId(), group.getId(), hostHolder.getUser().getUsername(), group.getGroupName());
        return group;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addGroup(Long userId, Long groupId, String username, String groupName) {
        groupMapper.insertUserGroup(userId, groupId);
        userSessionService.saveUserGroupSession(userId, groupId, username, groupName);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Long> getUserIdByGroupId(Long groupId) {
        return groupMapper.selectUserIdByGroupId(groupId);
    }

    @Override
    public GroupChat getById(Long groupId) {
        return groupMapper.selectById(groupId);
    }

    @Override
    public List<GroupChat> getGroup() {
        return groupMapper.selectGroupByUserId(hostHolder.getUser().getId());
    }
}
