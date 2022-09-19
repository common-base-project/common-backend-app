package top.mybi.code.generator.config;

import lombok.Data;

/**
 * @Description: 包相关的配置项
 *
 * @Author: mustang
 * @Date: 2021/12/30 15:24
 */
@Data
public class PackageConfig {

    private PackageConfig() {
    }

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "top.mybi";

    /**
     * 父包模块名
     */
    private String moduleName = "";



    /**
     * @Description: 构建者
     *
     * @Author: mustang
     * @Date: 2021/12/30 15:27
     */
    public static class Builder implements IConfigBuilder<PackageConfig> {

        private final PackageConfig packageConfig;

        public Builder() {
            this.packageConfig = new PackageConfig();
        }

        public Builder(String parent, String moduleName) {
            this();
            this.packageConfig.parent = parent;
            this.packageConfig.moduleName = moduleName;
        }

        /**
         * 指定父包名
         *
         * @param parent 父包名
         * @return this
         */
        public Builder parent(String parent) {
            this.packageConfig.parent = parent;
            return this;
        }

        /**
         * 指定模块名称
         *
         * @param moduleName 模块名
         * @return this
         */
        public Builder moduleName(String moduleName) {
            this.packageConfig.moduleName = moduleName;
            return this;
        }

        @Override
        public PackageConfig build() {
            return this.packageConfig;
        }
    }
}
