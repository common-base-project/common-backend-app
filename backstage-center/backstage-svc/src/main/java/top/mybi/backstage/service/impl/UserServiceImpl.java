package top.mybi.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.mybi.backstage.entity.UserEntity;
import top.mybi.backstage.entity.UserRoleEntity;
import top.mybi.backstage.enums.ErrorCode;
import top.mybi.backstage.mapper.UserMapper;
import top.mybi.backstage.mapper.UserRoleMapper;
import top.mybi.backstage.pojo.dto.user.AddUserDTO;
import top.mybi.backstage.pojo.dto.user.UpdateUserDTO;
import top.mybi.backstage.pojo.dto.user.UserPageDTO;
import top.mybi.backstage.pojo.vo.user.UserPageVO;
import top.mybi.backstage.service.IUserService;
import top.mybi.common.core.exception.ServiceException;
import top.mybi.common.core.model.BasePageVO;
import top.mybi.common.core.utils.NumberUtil;
import top.mybi.common.redis.template.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

import static top.mybi.common.core.constants.RedisConstants.BACKSTAGE_PREFIX;
import static top.mybi.common.core.constants.RedisConstants.serializeKey;

/**
 * 用户表接口实现类
 *
 * @author mustang
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

    @Resource
    private UserRoleMapper userRoleMapper;

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
    @Transactional
    public void addUser(AddUserDTO params) {
        if (Objects.nonNull(getUserByPhone(params.getPhone()))) {
            throw new ServiceException(ErrorCode.USER_ALREADY_EXISTS);
        }
        UserEntity userEntity = params.covertBean(UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(params.getPassword()));
        userMapper.insert(userEntity);
        //用户角色
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(userEntity.getUserId());
        userRoleEntity.setRoleId(params.getRoleId());
        userRoleMapper.insert(userRoleEntity);
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserDTO params) {
        UserEntity oldEntity = userMapper.selectById(params.getUserId());
        if (Objects.isNull(oldEntity)) {
            throw new ServiceException(ErrorCode.USER_NOT_FOUND);
        }
        UserEntity entity = params.covertBean(UserEntity.class);
        userMapper.updateById(entity);
        //删除用户角色
        LambdaQueryWrapper<UserRoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleEntity::getUserId,params.getUserId());
        userRoleMapper.delete(wrapper);
        //用户角色
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUserId(entity.getUserId());
        userRoleEntity.setRoleId(params.getRoleId());
        userRoleMapper.insert(userRoleEntity);
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

    @Override
    public void delUser(Long userId) {
        userMapper.deleteById(userId);
    }
}