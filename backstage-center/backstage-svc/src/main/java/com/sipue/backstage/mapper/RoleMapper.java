package com.sipue.backstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sipue.backstage.entity.RoleEntity;
import com.sipue.common.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表Mapper
 * 
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * @Description: 查询角色列表
     *
     * @Author: wangjunyu
     * @Date: 2022/8/1 10:12
     */
    List<Long> getUserRoles(@Param("userId") Long userId);
}
