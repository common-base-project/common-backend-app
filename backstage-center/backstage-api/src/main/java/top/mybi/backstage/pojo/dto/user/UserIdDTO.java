package top.mybi.backstage.pojo.dto.user;

import top.mybi.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @Description: 用户id请求参数
 *
 * @Author: mustang
 * @Date: 2022/8/16 15:30
 */
@ApiModel("后台用户id请求参数")
@Getter
@Setter
public class UserIdDTO extends BasePageDTO{

    @ApiModelProperty(value = "用户id",required = true)
    @NotEmpty(message = "用户id不能为空")
    private Long userId;
}

