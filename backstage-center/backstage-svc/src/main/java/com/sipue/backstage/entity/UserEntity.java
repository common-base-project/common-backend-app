package com.sipue.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sipue.common.mybatis.entity.LogicDeleteBaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表
 * 
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
@Data
@TableName("sys_user")
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
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户状态：1：正常，2：停用，3：锁定
	 */
	private Integer status;
	/**
	 * 上次登录时间
	 */
	private LocalDateTime lastLoginTime;

}
