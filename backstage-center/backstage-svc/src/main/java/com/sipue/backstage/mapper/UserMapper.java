package com.sipue.backstage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sipue.backstage.entity.UserEntity;
import com.sipue.backstage.pojo.vo.user.UserPageVO;
import com.sipue.common.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户表Mapper
 * 
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
public interface UserMapper extends CommonMapper<UserEntity> {
    /**
     * @Description: 分页查询用户表
     *
     * @Author: wangjunyu
     * @Date: 2022-07-11 17:05:16
     */
    IPage<UserPageVO> getSysUserPage(Page<UserPageVO> page, @Param("params") Map<String, Object> map);
}
