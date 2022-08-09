package com.sipue.backstage.controller;


import com.sipue.backstage.pojo.dto.user.AddUserDTO;
import com.sipue.backstage.pojo.dto.user.UserPageDTO;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.backstage.service.IUserService;
import com.sipue.common.core.model.BasePageVO;
import com.sipue.common.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 用户管理
 *
 * @Author: wangjunyu
 * @Date: 2022/7/11 15:24
 */
@Api(tags = "用户管理")
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/user/page")
    @ApiOperation(value = "分页获取用户列表")
    public Result<BasePageVO<UserPageVO>> getKnowledgePage(@RequestBody @Validated UserPageDTO params){
        return Result.success(userService.getUserPage(params));
    }

    @PostMapping("/user/add")
    @ApiOperation(value = "新增用户")
    public Result getKnowledgePage(@RequestBody @Validated AddUserDTO params){
        userService.addUser(params);
        return Result.success();
    }
}
