package com.sipue.backstage.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sipue.backstage.entity.MenuEntity;
import com.sipue.backstage.mapper.MenuMapper;
import com.sipue.backstage.pojo.dto.menu.AddMenuDTO;
import com.sipue.backstage.pojo.dto.menu.MenuIdDTO;
import com.sipue.backstage.pojo.dto.menu.UpdateMenuDTO;
import com.sipue.backstage.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表接口实现类
 *
 * @author wangjunyu
 * @date 2022-07-11 15:10:17
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity>  implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Tree<Long>> treeMenu(Long parentId) {
        List<TreeNode<Long>> collect = baseMapper
                .selectList(Wrappers.<MenuEntity>lambdaQuery().eq(MenuEntity::getParentId, parentId)
                        .orderByAsc(MenuEntity::getSortOrder))
                .stream().map(getNodeFunction()).collect(Collectors.toList());

        return TreeUtil.build(collect, parentId);
    }

    @NotNull
    private Function<MenuEntity, TreeNode<Long>> getNodeFunction() {
        return menu -> {
            TreeNode<Long> node = new TreeNode<>();
            node.setId(menu.getMenuId());
            node.setName(menu.getName());
            node.setParentId(menu.getParentId());
            node.setWeight(menu.getSortOrder());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("icon", menu.getIcon());
            extra.put("path", menu.getPath());
            extra.put("type", menu.getType());
            extra.put("permission", menu.getPermission());
            extra.put("label", menu.getName());
            extra.put("sortOrder", menu.getSortOrder());
            extra.put("keepAlive", menu.getKeepAlive());
            node.setExtra(extra);
            return node;
        };
    }

    @Override
    public void addMenu(AddMenuDTO params) {
        MenuEntity sysMenuEntity = params.covertBean(MenuEntity.class);
        baseMapper.insert(sysMenuEntity);
    }

    @Override
    public void updateMenu(UpdateMenuDTO params) {
        MenuEntity sysMenuEntity = params.covertBean(MenuEntity.class);
        baseMapper.updateById(sysMenuEntity);
    }

    @Override
    public void deleteMenu(MenuIdDTO params) {
        baseMapper.deleteById(params.getMenuId());
    }
}