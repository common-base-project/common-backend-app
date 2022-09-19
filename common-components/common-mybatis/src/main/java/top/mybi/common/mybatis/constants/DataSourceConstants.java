package top.mybi.common.mybatis.constants;

/**
 * 数据源相关常量
 * @Author mustang
 * @date 2022/10/28 18:07
 */
public interface DataSourceConstants {
    /**
     * 数据源名称
     */
    String DS_NAME = "name";

    /**
     * 默认数据源（master）
     */
    String DS_MASTER = "master";

    /**
     * jdbcurl
     */
    String DS_JDBC_URL = "url";

    /**
     * 用户名
     */
    String DS_USER_NAME = "username";

    /**
     * 密码
     */
    String DS_USER_PWD = "password";

    /**
     * 驱动包名称
     */
    String DS_DRIVER_CLASS_NAME = "driver_class_name";
}
