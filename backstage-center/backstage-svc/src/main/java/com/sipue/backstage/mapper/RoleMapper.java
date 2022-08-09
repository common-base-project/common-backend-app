package com.sipue.backstage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sipue.backstage.entity.RoleEntity;
import com.sipue.backstage.pojo.vo.role.RolePageVO;
import com.sipue.common.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 角色表Mapper
 * 
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
public interface RoleMapper extends CommonMapper<RoleEntity> {
    /**
     * @Description: 分页查询角色表
     *
     * @Author: wangjunyu
     * @Date: 2022-07-12 18:12:55
     */
    IPage<RolePageVO> getSysRolePage(Page<RolePageVO> page, @Param("params") Map<String, Object> map);
}
