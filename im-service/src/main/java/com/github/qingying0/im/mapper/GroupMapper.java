package com.github.qingying0.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingying0.im.entity.GroupChat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qingying0
 * @since 2020-03-21
 */
@Repository
public interface GroupMapper extends BaseMapper<GroupChat> {

    void insertUserGroup(@Param("userId") Long userId,@Param("groupId") Long groupId);

    List<Long> selectUserIdByGroupId(Long groupId);

    List<GroupChat> selectGroupByUserId(Long id);
}
