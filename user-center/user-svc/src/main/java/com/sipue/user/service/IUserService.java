package com.sipue.user.service;


import com.sipue.common.core.model.BasePageVO;
import com.sipue.user.pojo.vo.user.UserPageVO;
import com.sipue.user.pojo.dto.user.UserPageDTO;
import com.sipue.user.pojo.dto.user.AddUserDTO;
import com.sipue.user.pojo.dto.user.UpdateUserDTO;

/**
 * 用户
 *
 * @author wangjunyu
 * @date 2022-08-22 17:22:17
 */
public interface IUserService  {

    /**
     * @Description: 分页查询用户
     *
     * @Author: wangjunyu
     * @Date: 2022-08-22 17:22:17
     */
    BasePageVO<UserPageVO> getUserPage(UserPageDTO params);

    /**
     * @Description: 新增用户
     *
     * @Author: wangjunyu
     * @Date: 2022-08-22 17:22:17
     */
    void addUser(AddUserDTO params);

    /**
     * @Description: 修改用户
     *
     * @Author: wangjunyu
     * @Date: 2022-08-22 17:22:17
     */
    void updateUser(UpdateUserDTO params);

    /**
     * @Description: 删除用户
     *
     * @Author: wangjunyu
     * @Date: 2022-08-22 17:22:17
     */
    void deleteUser(Long userId);
}

