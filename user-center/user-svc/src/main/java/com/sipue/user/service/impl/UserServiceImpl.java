package com.sipue.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.sipue.user.service.IUserService;
import com.sipue.user.entity.UserEntity;
import com.sipue.user.mapper.UserMapper;
import com.sipue.user.pojo.vo.user.UserPageVO;
import com.sipue.user.pojo.dto.user.UserPageDTO;
import com.sipue.user.pojo.dto.user.AddUserDTO;
import com.sipue.user.pojo.dto.user.UpdateUserDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sipue.common.core.model.BasePageVO;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.common.core.exception.ServiceException;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 用户接口实现类
 *
 * @author wangjunyu
 * @date 2022-08-22 17:25:57
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public BasePageVO<UserPageVO> getUserPage(UserPageDTO params) {
        Page<UserPageVO> page = new Page<>(params.getCurrent(), params.getSize());
        Map<String, Object> map = params.covertMap();
        userMapper.getUserPage(page, map);
        BasePageVO<UserPageVO> pageVO = new BasePageVO<>();
        BeanUtils.copyProperties(page, pageVO);
        return pageVO;
    }

    @Override
    public void addUser(AddUserDTO params) {
        UserEntity entity = params.covertBean(UserEntity.class);
        userMapper.insert(entity);
    }

    @Override
    public void updateUser(UpdateUserDTO params) {
        UserEntity oldEntity = userMapper.selectById(params.getUserId());
        if(Objects.isNull(oldEntity)){
            throw new ServiceException(CommonErrorCode.VALIDATE_CODE_ERROR);
        }
        UserEntity entity = params.covertBean(UserEntity.class);
        userMapper.updateById(entity);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity entity = userMapper.selectById(userId);
        if(Objects.isNull(entity)){
            throw new ServiceException(CommonErrorCode.VALIDATE_CODE_ERROR);
        }
        userMapper.deleteById(entity);
    }

}