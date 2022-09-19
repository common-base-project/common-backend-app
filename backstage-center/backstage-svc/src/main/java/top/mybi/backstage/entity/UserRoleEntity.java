package top.mybi.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.mybi.common.mybatis.entity.LogicDeleteBaseEntity;
import lombok.Data;

/**
 * 用户角色表
 * 
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
@Data
@TableName("sys_user_role")
public class UserRoleEntity extends LogicDeleteBaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 角色id
	 */
	private Long roleId;
}
