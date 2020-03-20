package com.github.qingying0.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingying0.im.dto.MessageDTO;
import com.github.qingying0.im.entity.Message;
import org.springframework.stereotype.Repository;

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
public interface MessageMapper extends BaseMapper<Message> {

    List<MessageDTO> selectByTargetId(Long userId);
}
