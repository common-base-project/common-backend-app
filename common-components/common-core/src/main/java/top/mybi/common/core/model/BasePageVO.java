package top.mybi.common.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * @Description 基础分页响应参数
 * @Author mustang
 * @Date 2022年6月22日18:08:43
 * @Version 1.0
 */
@Setter
@Getter
public class BasePageVO<T> extends BaseModel{

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 查询数据列表
     * @response
     */
    private List<T> records = Collections.emptyList();
}
