package top.mybi.user.pojo.dto.user;

import top.mybi.common.core.model.BasePageDTO;
import lombok.Getter;
import lombok.Setter;
import io.swagger.annotations.ApiModel;
/**
 * 分页查询用户请求参数
 *
 * @author mustang
 * @date 2022-08-22 16:55:22
 */
@ApiModel("分页查询用户请求参数")
@Getter
@Setter
public class UserPageDTO extends BasePageDTO{

}

