package top.mybi.backstage.service;

import top.mybi.backstage.feign.UserFeignClient;
import top.mybi.backstage.pojo.dto.user.UserPageDTO;
import top.mybi.backstage.pojo.vo.user.UserPageVO;
import top.mybi.common.core.exception.ServiceException;
import top.mybi.common.core.model.BasePageVO;
import top.mybi.common.core.model.CommonErrorCode;
import top.mybi.common.core.model.Result;
import org.springframework.beans.factory.annotation.Autowired;

public class BackstageUserFeignService {

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * @Description: 批量查询用户信息
     *
     * @Author: mustang
     * @Date: 2022/7/22 14:21
     */
    public BasePageVO<UserPageVO> getUserListByIds(UserPageDTO params){
        Result<BasePageVO<UserPageVO>> result = userFeignClient.getUser(params);
        if (result.getCode().equals(CommonErrorCode.SUCCESS.getCode())) {
            return result.getData();
        } else {
            throw new ServiceException(result);
        }
    }
}

