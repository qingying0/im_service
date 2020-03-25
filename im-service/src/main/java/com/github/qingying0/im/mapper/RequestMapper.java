package com.github.qingying0.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingying0.im.dto.RequestDTO;
import com.github.qingying0.im.entity.Request;
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
public interface RequestMapper extends BaseMapper<Request> {

    Request selectBySendIdAndTargetId(Long sendId, Long targetId);

    List<RequestDTO> selectByTargetId(Long id);
}
