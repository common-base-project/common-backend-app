package top.mybi.backstage.pojo.dto.role;

import top.mybi.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * 新增角色参数
 *
 * @author mustang
 * @date 2022-07-12 18:12:55
 */
@ApiModel("新增角色参数")
@Getter
@Setter
public class AddRoleDTO extends BasePageDTO{

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty("描述")
    private String roleDesc;
}

