package com.sipue.backstage.pojo.dto.user;

import com.sipue.common.core.model.BasePageDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
/**
 * 分页查询用户表请求参数
 *
 * @author wangjunyu
 * @date 2022-07-11 17:05:16
 */
@ApiModel("分页查询用户表请求参数")
@Getter
@Setter
public class UserPageDTO extends BasePageDTO{

}

