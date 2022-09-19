package top.mybi.backstage.service;


import cn.hutool.core.lang.tree.Tree;
import top.mybi.backstage.entity.MenuEntity;
import top.mybi.backstage.pojo.dto.menu.AddMenuDTO;
import top.mybi.backstage.pojo.dto.menu.UpdateMenuDTO;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表
 *
 * @author mustang
 * @date 2022-07-11 15:10:17
 */
public interface IMenuService {

    /**
     * @Description: 查询菜单权限列表
     *
     * @Author: mustang
     * @Date: 2022/7/11 16:08
     */
    List<Tree<Long>> treeMenu(Long parentId);

    /**
     * @Description: 通过角色编号查询菜单
     *
     * @Author: mustang
     * @Date: 2022/8/11 17:47
     */
    Set<MenuEntity> getMenuByRoleId(Long roleId);

    /**
     * @Description: 查询菜单
     *
     * @Author: mustang
     * @Date: 2022/8/11 17:54
     */
    List<Tree<Long>> filterMenu(Set<MenuEntity> menuSet, Long parentId);

    /**
     * @Description: 新增菜单
     *
     * @Author: mustang
     * @Date: 2022/7/11 16:43
     */
    void addMenu(AddMenuDTO params);

    /**
     * @Description: 新增菜单
     *
     * @Author: mustang
     * @Date: 2022/7/11 16:43
     */
    void updateMenu(UpdateMenuDTO params);

    /**
     * @Description: 删除菜单
     *
     * @Author: mustang
     * @Date: 2022/7/11 16:43
     */
    void deleteMenu(Long menuId);
}

