package top.mybi.backstage.pojo.dto.role;

import top.mybi.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 角色id参数
 *
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
@ApiModel("角色id参数")
@Getter
@Setter
public class RoleIdDTO extends BasePageDTO{

    @ApiModelProperty(value = "id",required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;
}

