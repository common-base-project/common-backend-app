package com.sipue.backstage.service;


import com.sipue.backstage.entity.UserEntity;
import com.sipue.backstage.pojo.dto.user.AddUserDTO;
import com.sipue.backstage.pojo.dto.user.UpdateUserDTO;
import com.sipue.backstage.pojo.dto.user.UserPageDTO;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.common.core.model.BasePageVO;

/**
 * 用户表
 *
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
public interface IUserService {

    /**
     * @Description: 分页查询用户表
     *
     * @Author: mustang
     * @Date: 2022-07-11 17:05:16
     */
    BasePageVO<UserPageVO> getUserPage(UserPageDTO params);

    /**
     * @Description: 添加用户
     *
     * @Author: mustang
     * @Date: 2022/7/11 17:13
     */
    void addUser(AddUserDTO params);

    /**
     * @Description: 编辑用户
     *
     * @Author: mustang
     * @Date: 2022/8/16 15:34
     */
    void updateUser(UpdateUserDTO params);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    UserEntity getUserByPhone(String phone);

    /**
     * @Description: 删除用户
     *
     * @Author: mustang
     * @Date: 2022/8/29 15:34
     */
    void delUser(Long userId);
}

