package com.sipue.code.generator.config.model;

import lombok.Data;

import java.util.List;

/** 
 * @Description: 表数据
 *
 * @Author: mustang 
 * @Date: 2021/12/30 14:44
 */ 
@Data
public class Table {
    //表的名称
    private String tableName;
    //表的备注
    private String comments;
    //表的主键
    private Column pk;
    //表的列名(不包含主键)
    private List<Column> columns;
    //类名
    private String className;
    //包名
    private String packageName;
}
