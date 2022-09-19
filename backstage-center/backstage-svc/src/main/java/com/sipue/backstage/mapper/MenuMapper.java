package com.sipue.backstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sipue.backstage.entity.MenuEntity;

import java.util.Set;

/**
 * 菜单权限表Mapper
 * 
 * @author mustang
 * @date 2022-07-11 15:10:17
 */
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * @Description: 查询菜单权限列表
     *
     * @Author: mustang
     * @Date: 2022/8/11 17:48
     */
    Set<MenuEntity> getMenuByRoleId(Long roleId);
}
