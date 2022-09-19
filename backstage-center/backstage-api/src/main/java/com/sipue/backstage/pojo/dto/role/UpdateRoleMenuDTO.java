package com.sipue.backstage.pojo.dto.role;

import com.sipue.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 编辑角色菜单请求参数
 *
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
@ApiModel("编辑角色菜单请求参数")
@Getter
@Setter
public class UpdateRoleMenuDTO extends BasePageDTO{

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "菜单id列表")
    private List<Long> menuIds;
}

