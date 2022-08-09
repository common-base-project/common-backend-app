package com.sipue.backstage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sipue.backstage.mapper.RoleMapper;
import com.sipue.backstage.pojo.dto.role.RolePageDTO;
import com.sipue.backstage.pojo.vo.role.RolePageVO;
import com.sipue.backstage.service.IRoleService;
import com.sipue.common.core.model.BasePageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 角色表接口实现类
 *
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

    @Resource
    private RoleMapper sysRoleMapper;

    @Override
    public BasePageVO<RolePageVO> getRolePage(RolePageDTO params) {
        Page<RolePageVO> page = new Page<>(params.getCurrent(), params.getSize());
        Map<String, Object> map = params.covertMap();
        sysRoleMapper.getSysRolePage(page, map);
        BasePageVO<RolePageVO> pageVO = new BasePageVO<>();
        BeanUtils.copyProperties(page, pageVO);
        return pageVO;
    }
}