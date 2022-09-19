package top.mybi.backstage.controller;


import top.mybi.backstage.pojo.dto.user.AddUserDTO;
import top.mybi.backstage.pojo.dto.user.UpdateUserDTO;
import top.mybi.backstage.pojo.dto.user.UserIdDTO;
import top.mybi.backstage.pojo.dto.user.UserPageDTO;
import top.mybi.backstage.pojo.vo.user.UserPageVO;
import top.mybi.backstage.service.IUserService;
import top.mybi.common.core.model.BasePageVO;
import top.mybi.common.core.model.Result;
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
 * @Author: mustang
 * @Date: 2022/7/11 15:24
 */
@Api(tags = "用户管理")
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/user/page")
    @ApiOperation(value = "分页获取后台用户列表")
    public Result<BasePageVO<UserPageVO>> getUserPage(@RequestBody @Validated UserPageDTO params){
        return Result.success(userService.getUserPage(params));

    }

    @PostMapping("/user/add")
    @ApiOperation(value = "新增用户")
    public Result addUser(@RequestBody @Validated AddUserDTO params){
        userService.addUser(params);
        return Result.success();
    }

    @PostMapping("/user/update")
    @ApiOperation(value = "编辑用户")
    public Result updateUser(@RequestBody @Validated UpdateUserDTO params){
        userService.updateUser(params);
        return Result.success();
    }

    @PostMapping("/user/delete")
    @ApiOperation(value = "删除用户")
    public Result delUser(@RequestBody @Validated UserIdDTO params){
        userService.delUser(params.getUserId());
        return Result.success();
    }
}
