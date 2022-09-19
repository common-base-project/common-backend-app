package top.mybi.code.generator;

import top.mybi.code.generator.config.GlobalConfig;
import top.mybi.code.generator.config.PackageConfig;
import top.mybi.code.generator.jdbc.DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

public final class FastAutoGenerator {

    /**
     * 全局配置 Builder
     */
    private final GlobalConfig.Builder globalConfigBuilder;

    /**
     * 包配置 Builder
     */
    private final PackageConfig.Builder packageConfigBuilder;

    /**
     * 数据库连接池
     */
    private final Connection connection;


    private FastAutoGenerator(Connection connection){
        this.globalConfigBuilder = new GlobalConfig.Builder();
        this.packageConfigBuilder = new PackageConfig.Builder();
        this.connection = connection;
    }
    /**
     * 全局配置
     *
     * @param consumer 自定义全局配置
     * @return
     */
    public FastAutoGenerator globalConfig(Consumer<GlobalConfig.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    public FastAutoGenerator packageConfig(Consumer<PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    public static FastAutoGenerator create(String url, String username, String password) {
        return new FastAutoGenerator(DButil.getConnection(url,username,password));
    }


    /**
     * 生成执行方法入口
     */
    public void execute() throws SQLException {
        new AutoGenerator(this.connection)
                // 全局配置
                .global(this.globalConfigBuilder.build())
                // 包配置
                .packageInfo(this.packageConfigBuilder.build())
                // 执行
                .execute();
    }
}
