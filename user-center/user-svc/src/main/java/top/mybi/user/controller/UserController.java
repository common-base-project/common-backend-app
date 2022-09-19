package top.mybi.user.controller;


import top.mybi.common.core.model.BasePageVO;
import top.mybi.common.core.model.Result;
import top.mybi.user.service.IUserService;
import top.mybi.user.pojo.vo.user.UserPageVO;
import top.mybi.user.pojo.dto.user.UserPageDTO;
import top.mybi.user.pojo.dto.user.AddUserDTO;
import top.mybi.user.pojo.dto.user.UpdateUserDTO;
import top.mybi.user.pojo.dto.user.UserIdDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理
 *
 * @author mustang
 * @date 2022-08-22 17:46:13
 */
@Api(tags = "用户管理")
@RestController
public class UserController  {

    @Resource
    private IUserService userService;

    @PostMapping("/user/page")
    @ApiOperation(value = "分页获取用户列表")
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
    @ApiOperation(value = "修改用户")
    public Result updateUser(@RequestBody @Validated UpdateUserDTO params){
        userService.updateUser(params);
        return Result.success();
    }

    @PostMapping("/user/delete")
    @ApiOperation(value = "删除用户")
    public Result deleteUser(@RequestBody @Validated UserIdDTO params){
        userService.deleteUser(params.getUserId());
        return Result.success();
    }
}

