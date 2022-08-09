package com.sipue.backstage.pojo.dto.menu;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 查询菜单权限列表请求参数
 *
 * @author wangjunyu
 * @date 2022-07-11 15:10:17
 */
@ApiModel("查询菜单权限列表请求参数")
@Getter
@Setter
public class SysMenuListDTO extends BaseModel {

    @ApiModelProperty("父节点id")
    private Long parentId = -1L;
}

