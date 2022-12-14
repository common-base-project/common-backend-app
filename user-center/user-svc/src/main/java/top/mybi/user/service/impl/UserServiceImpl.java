package top.mybi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mybi.user.service.IUserService;
import top.mybi.user.entity.UserEntity;
import top.mybi.user.mapper.UserMapper;
import top.mybi.user.pojo.vo.user.UserPageVO;
import top.mybi.user.pojo.dto.user.UserPageDTO;
import top.mybi.user.pojo.dto.user.AddUserDTO;
import top.mybi.user.pojo.dto.user.UpdateUserDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.mybi.common.core.model.BasePageVO;
import top.mybi.common.core.model.CommonErrorCode;
import top.mybi.common.core.exception.ServiceException;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 用户接口实现类
 *
 * @author mustang
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
        UserEntity oldEntity = getUserByPhone(params.getPhone());
        if(!Objects.isNull(oldEntity)){
            throw new ServiceException(CommonErrorCode.ACCOUNT_USER_NOT_NULL);
        }
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

    @Override
    public UserEntity getUserByPhone(String phone) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getPhone, phone);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        return userEntity;
    }

}