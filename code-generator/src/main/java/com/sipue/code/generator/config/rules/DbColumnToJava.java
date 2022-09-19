/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.sipue.code.generator.config.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 表字段类型与java对应关系
 *
 * @Author: mustang
 * @Date: 2021/12/30 10:34
 */
@AllArgsConstructor
@Getter
public enum DbColumnToJava {

    //Integer
    TINYINT("tinyint", "Integer"),
    SMALLINT("smallint", "Integer"),
    INT("int", "Integer"),


    DATETIME("datetime", "LocalDateTime"),
    DATE("date", "LocalDate"),
    TIMESTAMP("int", "LocalDateTime"),

    BIGINT("bigint", "Long"),
    FLOAT("float", "Float"),
    DECIMAL("decimal", "BigDecimal"),

    //String
    VARCHAR("varchar", "String"),
    CHAR("char", "String"),
    TINYTEXT("tinytext", "String"),
    TEXT("text", "String"),
    LONGTEXT("longtext", "String")
    ;


    /**
     * 类型
     */
    private String dbType;

    /**
     * 包路径
     */
    private String javaType;

    /**
     * 将mysql字段转成java字段
     * @param dbType
     * @return
     */
    public static String coverJavaType(String dbType) {
        for (DbColumnToJava dbColumnToJava : DbColumnToJava.values()) {
            if (dbType.equals(dbColumnToJava.dbType)) {
                return dbColumnToJava.javaType;
            }
        }
        return DbColumnToJava.VARCHAR.javaType;
    }
}
