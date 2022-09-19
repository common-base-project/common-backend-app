package com.sipue.code.generator.config;

import lombok.Data;
import org.springframework.util.StringUtils;


/**
 * @Description: 全局配置
 *
 * @Author: mustang
 * @Date: 2021/12/30 15:09
 */
@Data
public class GlobalConfig {

    private GlobalConfig() {
    }

    /**
     * 生成文件的输出目录【 windows:D://  linux or mac:/tmp 】
     */
    private String outputDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "D://" : "/tmp";

    /**
     * 是否打开输出目录
     */
    private boolean open = true;

    /**
     * 作者
     */
    private String author = "mustang";


    /**
     * 开启 swagger 模式（默认 true）
     */
    private Boolean swagger = true;

    /**
     * 表名，多个用逗号隔开
     */
    private String tableName;


    /**
     * @Description: 全局配置构建
     *
     * @Author: mustang
     * @Date: 2021/12/30 15:22
     */
    public static class Builder implements IConfigBuilder<GlobalConfig> {

        private final GlobalConfig globalConfig;

        public Builder() {
            this.globalConfig = new GlobalConfig();
        }


        /**
         * 禁止打开输出目录
         */
        public Builder disableOpenDir() {
            this.globalConfig.open = false;
            return this;
        }

        /**
         * 输出目录
         */
        public Builder outputDir(String outputDir) {
            this.globalConfig.outputDir = outputDir;
            return this;
        }

        /**
         * 作者
         */
        public Builder author(String author) {
            this.globalConfig.author = author;
            return this;
        }


        /**
         * 开启/关闭 swagger 模式
         */
        public Builder enableSwagger(boolean enable) {
            this.globalConfig.swagger = enable;
            return this;
        }

        /**
         * 表名
         */
        public Builder tableName(String tableName) {
            this.globalConfig.tableName = tableName;
            return this;
        }

        @Override
        public GlobalConfig build() {
            if(StringUtils.isEmpty(this.globalConfig.getTableName())){
                throw new RuntimeException("tablename is null");
            }
            return this.globalConfig;
        }
    }
}
