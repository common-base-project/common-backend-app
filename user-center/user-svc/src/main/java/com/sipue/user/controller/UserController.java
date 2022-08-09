package com.sipue.user.controller;

import com.sipue.backstage.pojo.dto.user.UserPageDTO;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.backstage.service.BackstageUserFeignService;
import com.sipue.common.core.model.BasePageVO;
import com.sipue.common.core.model.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequiredArgsConstructor
@Api(tags = "用户中心--用户管理")
public class UserController {

    @Resource
    private BackstageUserFeignService userService;

    @PostMapping(value = "/user/remote")
    public Result<BasePageVO<UserPageVO>> test() {
        UserPageDTO userPageDTO = new UserPageDTO();
        return Result.success(userService.getUserListByIds(userPageDTO));
    }

    @PostMapping(value = "/v1/detail")
    public Result findById() {
        return Result.success();
    }

}
