package com.sipue.backstage.pojo.vo.menu;

import com.sipue.common.core.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Description: 菜单信息响应
 *
 * @Author: mustang
 * @Date: 2022/8/11 17:44
 */
@ApiModel("菜单信息响应")
@Getter
@Setter
public class MenuVO extends BaseModel{

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long menuId;
    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;
    /**
     * 菜单权限标识
     */
    @ApiModelProperty("菜单权限标识")
    private String permission;
    /**
     * 前端URL
     */
    @ApiModelProperty("前端URL")
    private String path;
    /**
     * 父菜单ID
     */
    @ApiModelProperty("父菜单ID")
    private Long parentId;
    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;
    /**
     * 排序值
     */
    @ApiModelProperty("排序值")
    private Integer sortOrder;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @ApiModelProperty("菜单类型 （0菜单 1按钮）")
    private String type;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

}

