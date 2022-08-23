package com.sipue.user.entity;

import com.sipue.common.mybatis.entity.LogicDeleteBaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户
 * 
 * @author wangjunyu
 * @date 2022-08-22 17:05:34
 */
@Data
@TableName("t_user")
public class UserEntity extends LogicDeleteBaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 头像
	 */
	private String icon;
	/**
	 * 用户状态：1：正常，2：停用
	 */
	private Integer status;
	/**
	 * 上次登录时间
	 */
	private LocalDateTime lastLoginTime;

}
