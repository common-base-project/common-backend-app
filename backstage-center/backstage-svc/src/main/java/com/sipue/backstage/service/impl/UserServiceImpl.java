package com.sipue.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sipue.backstage.entity.UserEntity;
import com.sipue.backstage.enums.ErrorCode;
import com.sipue.backstage.mapper.UserMapper;
import com.sipue.backstage.pojo.dto.user.AddUserDTO;
import com.sipue.backstage.pojo.dto.user.UserPageDTO;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.backstage.service.IUserService;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.BasePageVO;
import com.sipue.common.core.utils.NumberUtil;
import com.sipue.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

import static com.sipue.common.core.constants.RedisConstants.BACKSTAGE_PREFIX;
import static com.sipue.common.core.constants.RedisConstants.serializeKey;

/**
 * 用户表接口实现类
 *
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisRepository redisRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    private static String USER_ID_CACHE_KEY = "user:userId-%d";
    private static String USER_PHONE_CACHE_KEY = "user:phone-%s";

    @Override
    public BasePageVO<UserPageVO> getUserPage(UserPageDTO params) {
        Page<UserPageVO> page = new Page<>(params.getCurrent(), params.getSize());
        Map<String, Object> map = params.covertMap();
        userMapper.getSysUserPage(page, map);
        BasePageVO<UserPageVO> pageVO = new BasePageVO<>();
        BeanUtils.copyProperties(page, pageVO);
        return pageVO;
    }

    @Override
    public void addUser(AddUserDTO params) {
        if (Objects.nonNull(getUserByPhone(params.getPhone()))) {
            throw new ServiceException(ErrorCode.USER_ALREADY_EXISTS);
        }
        UserEntity userEntity = params.covertBean(UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(params.getPassword()));
        userMapper.insert(userEntity);
    }

    @Override
    public UserEntity getUserByPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return null;
        }
        String redisKey = serializeKey(BACKSTAGE_PREFIX, String.format(USER_PHONE_CACHE_KEY, phone));
        Long userId = redisRepository.getLong(redisKey);
        if (Objects.nonNull(userId)) {
            return getUser(userId);
        }
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getPhone, phone);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if (Objects.nonNull(userEntity)) {
            redisRepository.putLong(redisKey, userEntity.getUserId());
        }
        return userEntity;
    }

    private UserEntity getUser(Long userId) {
        if (!NumberUtil.isPositiveNum(userId)) {
            return null;
        }
        String redisKey = serializeKey(BACKSTAGE_PREFIX, String.format(USER_ID_CACHE_KEY, userId));
        UserEntity userEntity = redisRepository.getObject(redisKey, UserEntity.class);
        if (Objects.isNull(userEntity)) {
            userEntity = userMapper.selectById(userId);
            redisRepository.putObject(redisKey, userEntity);
        }
        return userEntity;
    }
}