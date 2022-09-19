package top.mybi.backstage.service;


import top.mybi.backstage.pojo.dto.auth.UserLoginDTO;
import top.mybi.backstage.pojo.vo.auth.UserLoginVO;

/**
 *
 *
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
public interface IAuthService {

    /**
     * @Description: 登录
     *
     * @Author: mustang
     * @Date: 2022/7/11 18:25
     */
    UserLoginVO login(UserLoginDTO params);
}

