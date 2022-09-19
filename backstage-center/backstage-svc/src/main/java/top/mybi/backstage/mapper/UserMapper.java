package top.mybi.backstage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.mybi.backstage.entity.UserEntity;
import top.mybi.backstage.pojo.vo.user.UserPageVO;
import top.mybi.common.mybatis.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 用户表Mapper
 * 
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
public interface UserMapper extends CommonMapper<UserEntity> {
    /**
     * @Description: 分页查询用户表
     *
     * @Author: mustang
     * @Date: 2022-07-11 17:05:16
     */
    IPage<UserPageVO> getSysUserPage(Page<UserPageVO> page, @Param("params") Map<String, Object> map);
}
