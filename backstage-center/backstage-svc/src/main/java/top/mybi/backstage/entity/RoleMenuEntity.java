package top.mybi.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import top.mybi.common.mybatis.entity.LogicDeleteBaseEntity;
import lombok.Data;

/**
 * 角色菜单表
 * 
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
@Data
@TableName("sys_role_menu")
public class RoleMenuEntity extends LogicDeleteBaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long roleId;
	/**
	 * 角色名
	 */
	private Long menuId;
}
