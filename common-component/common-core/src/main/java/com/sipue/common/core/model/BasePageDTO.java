package com.sipue.common.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description 基础分页请求参数
 * @Author mustang
 * @Date 2022年6月22日18:08:43
 * @Version 1.0
 */
@Setter
@Getter
public class BasePageDTO extends BaseModel{

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;
}
