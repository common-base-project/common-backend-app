package com.sipue.backstage.controller;


import com.sipue.backstage.pojo.dto.auth.UserLoginDTO;
import com.sipue.backstage.pojo.vo.auth.UserLoginVO;
import com.sipue.backstage.service.IAuthService;
import com.sipue.common.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 登录管理
 *
 * @Author: mustang
 * @Date: 2022/7/11 15:24
 */
@Api(tags = "登录管理")
@RestController
public class AuthController {

    @Resource
    private IAuthService authService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/auth/login")
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginDTO params){
        return Result.success(authService.login(params));
    }
}
