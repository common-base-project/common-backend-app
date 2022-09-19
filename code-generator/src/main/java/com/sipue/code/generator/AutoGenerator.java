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
package com.sipue.code.generator;

import com.sipue.code.generator.config.GlobalConfig;
import com.sipue.code.generator.config.PackageConfig;
import com.sipue.code.generator.config.model.Table;
import com.sipue.code.generator.jdbc.DBTableQuery;
import com.sipue.code.generator.util.GenUtils;
import com.sipue.code.generator.util.RuntimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.ZipOutputStream;

/**
 * @Description: 生成文件
 *
 * @Author: mustang
 * @Date: 2021/12/30 17:30
 */
@Slf4j
public class AutoGenerator {


    /**
     * 包 相关配置
     */
    private PackageConfig packageInfo;
    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;
    /**
     * 数据库连接池
     */
    private Connection connection;

    /**
     * 构造方法
     *
     * @param dataSourceConfig 数据库配置
     */
    public AutoGenerator(Connection dataSourceConfig) {
        //这个是必须参数,其他都是可选的,后续去除默认构造更改成final
        this.connection = dataSourceConfig;
    }


    /**
     * 指定包配置信息
     *
     * @param packageConfig 包配置
     * @return this
     * @since 3.5.0
     */
    public AutoGenerator packageInfo(PackageConfig packageConfig) {
        this.packageInfo = packageConfig;
        return this;
    }


    /**
     * 指定全局配置
     *
     * @param globalConfig 全局配置
     * @return this
     * @see 3.5.0
     */
    public AutoGenerator global(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }


    /**
     * 生成文件
     */
    public void execute() throws SQLException {
        log.info("==========================准备生成文件...==========================");
        Table table = DBTableQuery.queryTable(connection,globalConfig.getTableName());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        GenUtils.generatorCode(globalConfig,packageInfo,table,zip);
        try {
            IOUtils.closeQuietly(zip);
            byte[] data = outputStream.toByteArray();
            IOUtils.write(data,new FileOutputStream(new File(globalConfig.getOutputDir()+"思普代码生成.zip")));
            //打开文件目录
            if(globalConfig.isOpen()){
                RuntimeUtils.openDir(globalConfig.getOutputDir());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.close();
        }
        log.info("==========================文件生成完成！！！==========================");
    }

}
