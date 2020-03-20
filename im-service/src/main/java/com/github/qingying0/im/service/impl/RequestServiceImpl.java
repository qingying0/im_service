package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.entity.Request;
import com.github.qingying0.im.mapper.RequestMapper;
import com.github.qingying0.im.service.IRequestService;
import org.springframework.stereotype.Service;

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

}
