package com.sipue.common.core.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 滚动分页请求对象
 * @Author wangjunyu
 * @date 2022年6月30日09:38:34
 * @version 1.0
 */
@Setter
@Getter
@ApiModel(value = "滚动分页请求对象")
public class ScrollPageDTO extends BaseModel {

    /**
     * 上次查询数据Id
     */
    @ApiModelProperty(value = "上次查询数据Id")
    private Long lastId;

    /**
     * 默认查询条数
     */
    @ApiModelProperty(value = "默认查询条数(10)")
    private long size = 10;

}
