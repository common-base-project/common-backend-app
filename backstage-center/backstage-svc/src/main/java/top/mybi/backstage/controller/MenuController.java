package top.mybi.backstage.controller;


import cn.hutool.core.lang.tree.Tree;
import top.mybi.backstage.entity.MenuEntity;
import top.mybi.backstage.pojo.dto.menu.AddMenuDTO;
import top.mybi.backstage.pojo.dto.menu.MenuIdDTO;
import top.mybi.backstage.pojo.dto.menu.SysMenuListDTO;
import top.mybi.backstage.pojo.dto.menu.UpdateMenuDTO;
import top.mybi.backstage.service.IMenuService;
import top.mybi.common.auth.annotation.RequiresPermissions;
import top.mybi.common.core.constants.CommonConstants;
import top.mybi.common.core.model.Result;
import top.mybi.common.core.model.Session;
import top.mybi.common.core.model.role.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 系统菜单管理
 *
 * @Author: mustang
 * @Date: 2022/7/11 15:24
 */
@Api(tags = "系统菜单管理")
@RestController
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping("/menu/list")
    @ApiOperation(value = "获取系统菜单列表")
    public Result<List<Tree<Long>>> treeMenu(@RequestBody @Validated SysMenuListDTO params){
        return Result.success(menuService.treeMenu(params.getParentId()));
    }

    @PostMapping("/menu")
    @ApiOperation(value = "返回当前用户的树形菜单集合")
    public Result<List<Tree<Long>>> getUserMenu() {
        // 获取符合条件的菜单
        List<Long> roleIds = Session.getUser().getRoles().stream().map(RoleVO::getRoleId).collect(Collectors.toList());
        Set<MenuEntity> menuSet = roleIds.stream().map(menuService::getMenuByRoleId)
                .flatMap(Collection::stream).collect(Collectors.toSet());
        return Result.success(menuService.filterMenu(menuSet, CommonConstants.CATALOG));
    }

    @PostMapping("/menu/add")
    @ApiOperation(value = "新增菜单")
    @RequiresPermissions("sys_menu_add")
    public Result addMenu(@RequestBody @Validated AddMenuDTO params){
        menuService.addMenu(params);
        return Result.success();
    }

    @PostMapping("/menu/update")
    @ApiOperation(value = "编辑菜单")
    @RequiresPermissions("sys_menu_edit")
    public Result updateMenu(@RequestBody @Validated UpdateMenuDTO params){
        menuService.updateMenu(params);
        return Result.success();
    }

    @PostMapping("/menu/delete")
    @ApiOperation(value = "删除菜单")
    @RequiresPermissions("sys_menu_del")
    public Result deleteMenu(@RequestBody @Validated MenuIdDTO params){
        menuService.deleteMenu(params.getMenuId());
        return Result.success();
    }
}
