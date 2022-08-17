package com.sipue.backstage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sipue.backstage.entity.RoleEntity;
import com.sipue.backstage.pojo.dto.role.UpdateRoleMenuDTO;
import com.sipue.common.core.model.role.RoleVO;

import java.util.List;
import java.util.Set;

/**
 * 角色表
 *
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
public interface IRoleService extends IService<RoleEntity> {

    /**
     * @Description: 查询用户角色列表
     *
     * @Author: wangjunyu
     * @Date: 2022/8/1 10:08
     */
    List<RoleVO> getUserRoles(Long userId);

    /**
     * @Description: 查询角色列表
     *
     * @Author: wangjunyu
     * @Date: 2022/8/12 15:04
     */
    List<RoleVO> getRoleList();

    /**
     * @Description: 更改角色菜单
     *
     * @Author: wangjunyu
     * @Date: 2022/8/12 15:19
     */
    void updateRoleMenu(UpdateRoleMenuDTO params);

    /**
     * @Description: 获取角色权限
     *
     * @Author: wangjunyu
     * @Date: 2022/8/17 10:56
     */
    List<String> getRolePermissions(List<Long> roleIds);
}

