package top.mybi.user.service;


import top.mybi.common.core.model.BasePageVO;
import top.mybi.user.entity.UserEntity;
import top.mybi.user.pojo.vo.user.UserPageVO;
import top.mybi.user.pojo.dto.user.UserPageDTO;
import top.mybi.user.pojo.dto.user.AddUserDTO;
import top.mybi.user.pojo.dto.user.UpdateUserDTO;

/**
 * 用户
 *
 * @author mustang
 * @date 2022-08-22 17:22:17
 */
public interface IUserService  {

    /**
     * @Description: 分页查询用户
     *
     * @Author: mustang
     * @Date: 2022-08-22 17:22:17
     */
    BasePageVO<UserPageVO> getUserPage(UserPageDTO params);

    /**
     * @Description: 新增用户
     *
     * @Author: mustang
     * @Date: 2022-08-22 17:22:17
     */
    void addUser(AddUserDTO params);

    /**
     * @Description: 修改用户
     *
     * @Author: mustang
     * @Date: 2022-08-22 17:22:17
     */
    void updateUser(UpdateUserDTO params);

    /**
     * @Description: 删除用户
     *
     * @Author: mustang
     * @Date: 2022-08-22 17:22:17
     */
    void deleteUser(Long userId);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    UserEntity getUserByPhone(String phone);
}

