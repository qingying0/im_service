package com.github.qingying0.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.entity.Message;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
public interface IMessageService extends IService<Message> {


    List<MessageDTO> getByTargetId(Long userId);
}
