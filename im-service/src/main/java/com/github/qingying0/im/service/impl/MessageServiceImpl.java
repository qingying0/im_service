package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.entity.Message;
import com.github.qingying0.im.mapper.MessageMapper;
import com.github.qingying0.im.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageDTO> getByTargetId(Long userId) {
        return messageMapper.selectByTargetId(userId);
    }
}
