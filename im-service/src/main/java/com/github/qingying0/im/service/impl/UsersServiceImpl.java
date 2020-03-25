package com.github.qingying0.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.qingying0.im.component.HostHolder;
import com.github.qingying0.im.dao.RedisDao;
import com.github.qingying0.im.dto.UserDTO;
import com.github.qingying0.im.dto.UserInfoDTO;
import com.github.qingying0.im.entity.Users;
import com.github.qingying0.im.exception.CustomCode;
import com.github.qingying0.im.exception.CustomException;
import com.github.qingying0.im.mapper.UsersMapper;
import com.github.qingying0.im.service.IRelationService;
import com.github.qingying0.im.service.IUsersService;
import com.github.qingying0.im.utils.IdWorker;
import com.github.qingying0.im.utils.MD5utils;
import com.github.qingying0.im.utils.RedisKeyUtils;
import com.github.qingying0.im.utils.SystemConst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qingying0
 * @since 2020-02-24
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private IRelationService relationService;

    @Autowired
    private HostHolder hostHolder;
    /**
     * 用户登陆
     * @param phone
     * @param password
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserDTO login(String phone, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("phone", phone);
        Users user = usersMapper.selectOne(wrapper);
        if(user == null) {
            throw new CustomException("用户不存在");
        }
        if(!user.getPassword().equals(MD5utils.MD5(password + user.getSalt()))) {
            throw new CustomException("密码错误");
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        //通过雪花算法获取一个唯一的值作为token
        String token = String.valueOf(idWorker.nextId());
        //redis保存token
        redisDao.set(RedisKeyUtils.getTokenKey(token), user, 60 * 60 * 24 * 30);
        userDTO.setToken(token);
        return userDTO;
    }

    /**
     * 用户注册
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void register(Users user) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("phone", user.getPhone());
        if(usersMapper.selectOne(wrapper) != null) {
            throw new CustomException("手机号已经注册");
        }
        user.setId(idWorker.nextId());
        user.setSalt(UUID.randomUUID().toString().substring(0, 6));
        user.setStatus(SystemConst.USER_EXIST);
        // 加salt再用md5加密
        user.setPassword(MD5utils.MD5(user.getPassword() + user.getSalt()));
        usersMapper.insert(user);
    }

    /**
     * 根据id查找用户，返回一个用户信息对象
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserInfoDTO getUserById(Long id) {
        Users user = usersMapper.selectById(id);
        if(user == null) {
            throw new CustomException("用户不存在");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        userInfoDTO.setFriend(relationService.getByUserAndFriend(hostHolder.getUser().getId(), user.getId()) != null);
        return userInfoDTO;
    }

    @Override
    public List<UserDTO> search(String search) {
        search = "%" + search + "%";
        return usersMapper.selectBySearch(search);
    }
}
