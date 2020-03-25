package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dao.RedisDao;
import com.github.qingying0.im.dto.RequestDTO;
import com.github.qingying0.im.entity.Relation;
import com.github.qingying0.im.entity.Request;
import com.github.qingying0.im.enums.RequestStatusEnum;
import com.github.qingying0.im.exception.CustomCode;
import com.github.qingying0.im.exception.CustomException;
import com.github.qingying0.im.mapper.RequestMapper;
import com.github.qingying0.im.service.IRelationService;
import com.github.qingying0.im.service.IRequestService;
import com.github.qingying0.im.service.IUserSessionService;
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
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private IRelationService relationService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IUserSessionService userSessionService;

    /**
     * 保存好友请求，设置请求状态为已发送
     * @param request
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveFriendRequest(Request request) {
        request.setSendId(hostHolder.getUser().getId());
        if(request.getSendId().equals(request.getTargetId())) {
            throw new CustomException("不能添加自己为好友");
        }
        //查看是否已经为好友
        Relation relation = relationService.getByUserAndFriend(request.getSendId(), request.getTargetId());
        if(relation != null && relation.getStatus() == 1) {
            throw new CustomException("已经添加为好友");
        }
        Request exist = requestMapper.selectBySendIdAndTargetId(request.getSendId(), request.getTargetId());
        if(exist != null) {
            if(exist.getStatus().equals(RequestStatusEnum.SEND.status)) {
                throw new CustomException("已经发送好友请求,不能重复发送");
            }
            if(exist.getStatus().equals(RequestStatusEnum.FAIL.status)) {
                throw new CustomException("好友请求被拒绝");
            }
            throw new CustomException(CustomCode.FAIL);
        }
        request.setId(idWorker.nextId());
        request.setType(SystemConst.REQUEST_FRIEND);
        request.setStatus(RequestStatusEnum.SEND.status);
        requestMapper.insert(request);
        redisDao.lSetLeft(RedisKeyUtils.getTargetRequestKey(request.getTargetId()), request);
    }

    /**
     * 更新好友请求状态
     * @param requestId
     * @param status
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Request updateFriendRequest(Long requestId, Integer status) {
        Request request = requestMapper.selectById(requestId);
        if(request == null) {
            throw new CustomException(CustomCode.FAIL);
        }
        // 请求成功，添加好友和会话表
        if(status.equals(RequestStatusEnum.SUCCESS.status)) {
            relationService.saveRelation(request.getSendId(), request.getTargetId());
            userSessionService.saveUserSession(request.getSendId(), request.getTargetId());
            request.setStatus(status);
            requestMapper.updateById(request);
            return request;
        }
        if(status.equals(RequestStatusEnum.FAIL.status)) {
            request.setStatus(status);
            requestMapper.updateById(request);
            return request;
        }
        return request;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<RequestDTO> getRequests() {
        return requestMapper.selectByTargetId(hostHolder.getUser().getId());
    }
}
