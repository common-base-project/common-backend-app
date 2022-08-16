package com.sipue.backstage.pojo.dto.user;

import com.sipue.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询后台用户表请求参数
 *
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
@ApiModel("分页查询后台用户表请求参数")
@Getter
@Setter
public class UserPageDTO extends BasePageDTO{

    @ApiModelProperty(value = "搜索值")
    private String searchVal;
}

