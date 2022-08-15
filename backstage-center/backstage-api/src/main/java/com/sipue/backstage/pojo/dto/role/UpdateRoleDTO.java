package com.sipue.backstage.pojo.dto.role;

import com.sipue.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编辑角色参数
 *
 * @author wangjunyu
 * @date 2022-07-12 18:12:55
 */
@ApiModel("编辑角色参数")
@Getter
@Setter
public class UpdateRoleDTO extends BasePageDTO{

    @ApiModelProperty("角色名")
    private Long roleId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String roleDesc;
}

