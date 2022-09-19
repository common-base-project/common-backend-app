package com.sipue.common.core.model.role;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色表响应
 *
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
@ApiModel("角色表响应")
@Getter
@Setter
public class RoleVO extends BaseModel{

    @ApiModelProperty("id")
    private Long roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String roleDesc;

}

