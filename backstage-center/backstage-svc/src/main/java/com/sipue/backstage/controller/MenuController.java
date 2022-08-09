package com.sipue.backstage.controller;


import cn.hutool.core.lang.tree.Tree;
import com.sipue.backstage.pojo.dto.menu.SysMenuListDTO;
import com.sipue.backstage.service.IMenuService;
import com.sipue.common.core.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 系统菜单管理
 *
 * @Author: wangjunyu
 * @Date: 2022/7/11 15:24
 */
@Api(tags = "系统菜单管理")
@RestController
public class MenuController {

    @Resource
    private IMenuService menuService;

    @PostMapping("/menu/list")
    @ApiOperation(value = "获取系统菜单列表")
    public Result<List<Tree<Long>>> getKnowledgePage(@RequestBody @Validated SysMenuListDTO params){
        return Result.success(menuService.treeMenu(params.getParentId()));
    }
}
