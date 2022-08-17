package com.sipue.user.controller;

import com.sipue.backstage.pojo.dto.user.UserPageDTO;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.backstage.service.BackstageUserFeignService;
import com.sipue.common.core.constants.MessageQueueConst;
import com.sipue.common.core.model.BasePageVO;
import com.sipue.common.core.model.Result;
import com.sipue.common.rabbitmq.data.RabbitData;
import com.sipue.common.rabbitmq.provider.RabbitProvider;
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

    private final RabbitProvider rabbitProvider;

    @PostMapping(value = "/user/remote")
    public Result<BasePageVO<UserPageVO>> test() {
        UserPageDTO userPageDTO = new UserPageDTO();
        return Result.success(userService.getUserListByIds(userPageDTO));
    }

    @PostMapping(value = "/user/message")
    public Result message() {
        RabbitData rabbitData=new RabbitData();
        rabbitData.setUuid("asfdgf879fa");
        rabbitProvider.send(MessageQueueConst.SMS_BACK_EXCHANGE, MessageQueueConst.SMS_BACK_ROUTE, rabbitData);
        return Result.success();
    }

    @PostMapping(value = "/v1/detail")
    public Result findById() {
        return Result.success();
    }

}
