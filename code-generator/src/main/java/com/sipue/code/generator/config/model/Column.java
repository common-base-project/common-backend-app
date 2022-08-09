package com.sipue.code.generator.config.model;

import lombok.Data;

/**
 * @Description: 列的属性
 *
 * @Author: wangjunyu
 * @Date: 2021/12/30 14:42
 */
@Data
public class Column {
	//列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String comments;
    
    //属性名称(第一个字母大写)
    private String attrName;
    //列名对应java的名字  比如user_name 就是 userName
    private String columnJavaName;
    //属性类型
    private String attrType;
    //auto_increment
    private String extra;
}
