package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dao.RedisDao;
import com.github.qingying0.im.dto.FriendDTO;
import com.github.qingying0.im.entity.Relation;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.mapper.RelationMapper;
import com.github.qingying0.im.service.IRelationService;
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
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements IRelationService {

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private RedisDao redisDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendDTO> getFriends() {
        List<FriendDTO> friendDTOS = relationMapper.selectByUserId(hostHolder.getUser().getId());
        for(FriendDTO friendDTO : friendDTOS) {
            if(redisDao.sHasKey(RedisKeyUtils.getOnlineKey(), friendDTO.getId())) {
                friendDTO.setStatus(SystemConst.FRIEND_ONLINE);
            } else {
                friendDTO.setStatus(SystemConst.FRIEND_OFFLINE);
            }
        }
        return friendDTOS;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Relation getByUserAndFriend(Long userId, Long targetId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("send_id", userId);
        wrapper.eq("target_id", targetId);
        return relationMapper.selectOne(wrapper);
    }
}
