package com.sipue.backstage.pojo.vo.auth;


import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户对象
 * @author  邓军
 * @date 2019年12月30日19:14:01
 * @version 1.0
 */
@ApiModel(value = "用户对象")
@Setter
@Getter
public class LoginUserVO extends BaseModel {
    /**
     * 主键：用户ID
     */
    @ApiModelProperty(value = "主键：用户ID")
    private Long userId;

    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private List<Long> roleIds;
}
