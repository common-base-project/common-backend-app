package top.mybi.backstage.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mybi.backstage.entity.MenuEntity;
import top.mybi.backstage.mapper.MenuMapper;
import top.mybi.backstage.pojo.dto.menu.AddMenuDTO;
import top.mybi.backstage.pojo.dto.menu.UpdateMenuDTO;
import top.mybi.backstage.service.IMenuService;
import top.mybi.common.core.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表接口实现类
 *
 * @author mustang
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

    @Override
    public Set<MenuEntity> getMenuByRoleId(Long roleId) {
        return menuMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<Tree<Long>> filterMenu(Set<MenuEntity> menuSet, Long parentId) {
        List<TreeNode<Long>> collect = menuSet.stream()
                .filter(menu -> StrUtil.isNotBlank(menu.getPath())).map(getNodeFunction()).collect(Collectors.toList());
        Long parent = parentId == null ? CommonConstants.CATALOG : parentId;
        return TreeUtil.build(collect, parent);
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
    public void deleteMenu(Long menuId) {
        baseMapper.deleteById(menuId);
    }
}