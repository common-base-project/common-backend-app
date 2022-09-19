package top.mybi.backstage.pojo.vo.auth;


import top.mybi.common.core.model.role.RoleVO;
import top.mybi.common.core.model.BaseModel;
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
     * 所属角色
     */
    @ApiModelProperty(value = "所属角色")
    private List<RoleVO> roles;

    @ApiModelProperty(value = "权限")
    private List<String> perissions;
}
