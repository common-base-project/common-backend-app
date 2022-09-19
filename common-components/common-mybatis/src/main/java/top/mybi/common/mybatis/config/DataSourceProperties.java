package top.mybi.common.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据库配置信息
 * @Author mustang
 * @date 2022/10/28 17:50
 */
@Data
@ConfigurationProperties("spring.datasource")
public class DataSourceProperties {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * jdbcurl
     */
    private String url;

    /**
     * 驱动类型
     */
    private String driverClassName;

    /**
     * 查询数据源的SQL
     */
    private String queryDsSql = "select * from gen_datasource_conf where del_flag = 0";
}
