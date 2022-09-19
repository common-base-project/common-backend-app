package top.mybi.user.entity;

import top.mybi.common.mybatis.entity.LogicDeleteBaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户
 * 
 * @author mustang
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
	 * 微信号
	 */
	private String wechat;
	/**
	 * 性别:1.男;2.女
	 */
	private Integer sex;
	/**
	 * 生日
	 */
	private LocalDate birthday;

	// =================特殊属性================
	/**
	 * 是否为vip:1.是;2.否
	 */
	private Integer viper;
	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 邀请人
	 */
	private String inviter;
	/**
	 * 余额
	 */
	private BigDecimal balance;

	/**
	 * 上次登录时间
	 */
	private LocalDateTime lastLoginTime;

}
