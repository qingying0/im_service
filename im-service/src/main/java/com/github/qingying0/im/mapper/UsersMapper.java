package com.github.qingying0.im.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.qingying0.im.dto.UserDTO;
import com.github.qingying0.im.entity.Users;
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
public interface UsersMapper extends BaseMapper<Users> {

    List<UserDTO> selectBySearch(String search);
}
