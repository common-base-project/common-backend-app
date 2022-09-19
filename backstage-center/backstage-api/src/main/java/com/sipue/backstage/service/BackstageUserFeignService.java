package com.sipue.backstage.service;

import com.sipue.backstage.feign.UserFeignClient;
import com.sipue.backstage.pojo.dto.user.UserPageDTO;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.common.core.exception.ServiceException;
import com.sipue.common.core.model.BasePageVO;
import com.sipue.common.core.model.CommonErrorCode;
import com.sipue.common.core.model.Result;
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

