package top.mybi.backstage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.mybi.backstage.entity.RoleEntity;
import top.mybi.common.core.model.role.RoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表Mapper
 * 
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * @Description: 查询角色列表
     *
     * @Author: mustang
     * @Date: 2022/8/1 10:12
     */
    List<RoleVO> getUserRoles(@Param("userId") Long userId);
}
