package top.mybi.user.mapper;

import top.mybi.user.entity.UserEntity;
import top.mybi.user.pojo.vo.user.UserPageVO;
import top.mybi.common.mybatis.mapper.CommonMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

/**
 * 用户Mapper
 * 
 * @author mustang
 * @date 2022-08-22 17:05:34
 */
public interface UserMapper extends CommonMapper<UserEntity> {
    /**
     * @Description: 分页查询用户
     *
     * @Author: mustang
     * @Date: 2022-08-22 17:05:34
     */
    IPage<UserPageVO> getUserPage(Page<UserPageVO> page, @Param("params") Map<String, Object> map);
}
