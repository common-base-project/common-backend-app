package com.sipue.backstage.pojo.dto.menu;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加菜单请求对象
 *
 * @author mustang
 * @date 2022-07-11 15:10:17
 */
@ApiModel("添加菜单请求对象参数")
@Getter
@Setter
public class AddMenuDTO extends BaseModel {

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单权限标识
     */
    @ApiModelProperty(value = "菜单名称")
    private String permission;

    /**
     * 前端URL
     */
    @ApiModelProperty(value = "前端URL")
    private String path;

    /**
     * 父菜单ID
     */
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId = -1L;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 排序值
     */
    @ApiModelProperty(value = "排序值")
    private Integer sortOrder;

    /**
     * 0-开启，1- 关闭
     */
    @ApiModelProperty(value = "0-开启，1- 关闭")
    private String keepAlive;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @ApiModelProperty(value = "菜单类型 （0菜单 1权限）")
    private String type;
}

