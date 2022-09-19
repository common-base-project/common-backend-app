package com.sipue.backstage.entity;

import com.sipue.common.mybatis.entity.LogicDeleteBaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;

/**
 * 角色表
 * 
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
@Data
@TableName("sys_role")
public class RoleEntity extends LogicDeleteBaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "role_id", type = IdType.ASSIGN_ID)
	private Long roleId;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 角色编码
	 */
	private String roleCode;
	/**
	 * 描述
	 */
	private String roleDesc;

}
