package com.github.qingying0.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qingying0.im.dto.RequestDTO;
import com.github.qingying0.im.entity.Request;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
public interface IRequestService extends IService<Request> {

    List<RequestDTO> getRequests();

    void saveFriendRequest(Request request);

    Request updateFriendRequest(Long requestId, Integer status);
}
