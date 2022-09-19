package top.mybi.backstage.controller;


import top.mybi.backstage.entity.RoleEntity;
import top.mybi.backstage.pojo.dto.role.AddRoleDTO;
import top.mybi.backstage.pojo.dto.role.RoleIdDTO;
import top.mybi.backstage.pojo.dto.role.UpdateRoleDTO;
import top.mybi.backstage.pojo.dto.role.UpdateRoleMenuDTO;
import top.mybi.common.auth.annotation.RequiresPermissions;
import top.mybi.common.core.model.role.RoleVO;
import top.mybi.backstage.service.IRoleService;
import top.mybi.common.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 角色管理
 *
 * @Author: mustang
 * @Date: 2022/8/12 15:01
 */
@Api(tags = "角色管理")
@RestController
public class RoleController {

    @Resource
    private IRoleService roleService;


    @PostMapping("/role/list")
    @ApiOperation(value = "查询角色列表")
    public Result<List<RoleVO>> getRoleList(){
        return Result.success(roleService.getRoleList());
    }

    @PostMapping("/role/add")
    @ApiOperation(value = "新增角色")
    @RequiresPermissions("sys_role_add")
    public Result addRole(@RequestBody @Validated AddRoleDTO params){
        RoleEntity entity = params.covertBean(RoleEntity.class);
        roleService.save(entity);
        return Result.success();
    }

    @PostMapping("/role/update")
    @ApiOperation(value = "修改角色")
    @RequiresPermissions("sys_role_edit")
    public Result updateRole(@RequestBody @Validated UpdateRoleDTO params){
        RoleEntity entity = params.covertBean(RoleEntity.class);
        roleService.updateById(entity);
        return Result.success();
    }

    @PostMapping("/role/delete")
    @ApiOperation(value = "删除角色")
    @RequiresPermissions("sys_role_del")
    public Result delRole(@RequestBody @Validated RoleIdDTO params){
        roleService.removeById(params.getRoleId());
        return Result.success();
    }

    @PostMapping("/role/menu/update")
    @ApiOperation(value = "更新角色菜单")
    @RequiresPermissions("sys_role_edit")
    public Result updateRoleMenu(@RequestBody @Validated UpdateRoleMenuDTO params){
        roleService.updateRoleMenu(params);
        return Result.success();
    }
}
