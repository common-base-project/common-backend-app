package com.sipue.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sipue.common.mybatis.entity.LogicDeleteBaseEntity;
import lombok.Data;

/**
 * 菜单权限表
 * 
 * @author wangjunyu
 * @date 2022-07-11 15:10:17
 */
@Data
@TableName("sys_menu")
public class MenuEntity extends LogicDeleteBaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "menu_id", type = IdType.ASSIGN_ID)
	private Long menuId;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单权限标识
	 */
	private String permission;
	/**
	 * 前端URL
	 */
	private String path;
	/**
	 * 父菜单ID
	 */
	private Long parentId;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 排序值
	 */
	private Integer sortOrder;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	private String type;

}
