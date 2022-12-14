package top.mybi.backstage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mybi.backstage.entity.MenuEntity;
import top.mybi.backstage.entity.RoleEntity;
import top.mybi.backstage.entity.RoleMenuEntity;
import top.mybi.backstage.mapper.RoleMapper;
import top.mybi.backstage.mapper.RoleMenuMapper;
import top.mybi.backstage.pojo.dto.role.UpdateRoleMenuDTO;
import top.mybi.backstage.service.IMenuService;
import top.mybi.backstage.service.IRoleService;
import top.mybi.common.core.constants.NumberConstants;
import top.mybi.common.core.model.role.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表接口实现类
 *
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {

    @Resource
    private RoleMapper sysRoleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Override
    public List<RoleVO> getUserRoles(Long userId) {
        return sysRoleMapper.getUserRoles(userId);
    }

    @Override
    public List<RoleVO> getRoleList() {
        List<RoleEntity> list = list(Wrappers.emptyWrapper());
        return list.stream().map(e->e.covertBean(RoleVO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateRoleMenu(UpdateRoleMenuDTO params) {
        roleMenuMapper.delete(Wrappers.<RoleMenuEntity>query().lambda().eq(RoleMenuEntity::getRoleId,params.getRoleId()));
        if(!CollectionUtils.isEmpty(params.getMenuIds())){
            List<RoleMenuEntity> roleMenuList = params.getMenuIds().stream().map(menuId -> {
                RoleMenuEntity roleMenu = new RoleMenuEntity();
                roleMenu.setRoleId(params.getRoleId());
                roleMenu.setMenuId(menuId);
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.batchInsert(roleMenuList);
        }
    }

    @Override
    public List<String> getRolePermissions(List<Long> roleIds) {
        List<String> permissions = roleIds.stream().map(menuService::getMenuByRoleId).flatMap(Collection::stream)
                .filter(m -> NumberConstants.ONE.equals(m.getType())).map(MenuEntity::getPermission)
                .filter(StrUtil::isNotBlank).collect(Collectors.toList());
        return permissions;
    }
}