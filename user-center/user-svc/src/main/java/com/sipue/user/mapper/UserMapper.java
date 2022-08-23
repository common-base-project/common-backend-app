package com.sipue.user.mapper;

import com.sipue.user.entity.UserEntity;
import com.sipue.user.pojo.vo.user.UserPageVO;
import com.sipue.common.mybatis.mapper.CommonMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * 用户Mapper
 * 
 * @author wangjunyu
 * @date 2022-08-22 17:05:34
 */
public interface UserMapper extends CommonMapper<UserEntity> {
    /**
     * @Description: 分页查询用户
     *
     * @Author: wangjunyu
     * @Date: 2022-08-22 17:05:34
     */
    IPage<UserPageVO> getUserPage(Page<UserPageVO> page, @Param("params") Map<String, Object> map);
}
