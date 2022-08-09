package com.sipue.backstage.pojo.dto.menu;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 菜单id请求参数
 *
 * @author wangjunyu
 * @date 2022-07-11 15:10:17
 */
@ApiModel("菜单id请求参数")
@Getter
@Setter
public class MenuIdDTO extends BaseModel {

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true)
    @NotNull(message = "菜单id")
    private Long menuId;
}

