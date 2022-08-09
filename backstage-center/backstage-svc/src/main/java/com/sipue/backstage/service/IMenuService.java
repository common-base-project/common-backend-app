package com.sipue.backstage.service;


import cn.hutool.core.lang.tree.Tree;
import com.sipue.backstage.pojo.dto.menu.AddMenuDTO;
import com.sipue.backstage.pojo.dto.menu.MenuIdDTO;
import com.sipue.backstage.pojo.dto.menu.UpdateMenuDTO;

import java.util.List;

/**
 * 菜单权限表
 *
 * @author wangjunyu
 * @date 2022-07-11 15:10:17
 */
public interface IMenuService {

    /**
     * @Description: 查询菜单权限列表
     *
     * @Author: wangjunyu
     * @Date: 2022/7/11 16:08
     */
    List<Tree<Long>> treeMenu(Long parentId);

    /**
     * @Description: 新增菜单
     *
     * @Author: wangjunyu
     * @Date: 2022/7/11 16:43
     */
    void addMenu(AddMenuDTO params);

    /**
     * @Description: 新增菜单
     *
     * @Author: wangjunyu
     * @Date: 2022/7/11 16:43
     */
    void updateMenu(UpdateMenuDTO params);

    /**
     * @Description: 删除菜单
     *
     * @Author: wangjunyu
     * @Date: 2022/7/11 16:43
     */
    void deleteMenu(MenuIdDTO params);
}

