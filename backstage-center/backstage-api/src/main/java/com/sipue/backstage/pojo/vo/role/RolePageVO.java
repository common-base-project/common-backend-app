package com.sipue.backstage.pojo.vo.role;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询角色表响应
 *
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
@ApiModel("分页查询角色表响应")
@Getter
@Setter
public class RolePageVO extends BaseModel{

    @ApiModelProperty("id")
    private Long roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String roleDesc;

}

