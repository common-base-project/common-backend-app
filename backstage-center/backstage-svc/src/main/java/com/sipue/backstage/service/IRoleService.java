package com.sipue.backstage.service;


import com.sipue.backstage.pojo.dto.role.RolePageDTO;
import com.sipue.backstage.pojo.vo.role.RolePageVO;
import com.sipue.common.core.model.BasePageVO;

/**
 * 角色表
 *
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
public interface IRoleService  {

    /**
     * @Description: 分页查询角色表
     *
     * @Author: wangjunyu
     * @Date: 2022-07-12 18:12:55
     */
    BasePageVO<RolePageVO> getRolePage(RolePageDTO params);
}

