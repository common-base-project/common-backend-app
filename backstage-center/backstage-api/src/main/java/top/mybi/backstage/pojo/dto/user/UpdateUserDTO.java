package top.mybi.backstage.pojo.dto.user;

import top.mybi.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 编辑后台用户表请求参数
 *
 * @author mustang
 * @date 2022-07-11 17:05:16
 */
@ApiModel("编辑后台用户表请求参数")
@Getter
@Setter
public class UpdateUserDTO extends BasePageDTO{

    @ApiModelProperty(value = "用户id",required = true)
    @NotEmpty(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty(value = "角色id",required = true)
    @NotEmpty(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "状态:1.正常;2.停用",required = true)
    @NotEmpty(message = "状态不能为空")
    private Integer status;
}

