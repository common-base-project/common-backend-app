package top.mybi.code.generator;

import java.sql.SQLException;

/**
 * @Description: 代码生成启动类
 *
 * 注意：
 * 1.被生成的表一定要有主键
 * 2.暂时只支持单张表进行生成，多表请多次生成，后面有时间改改
 * 3.生成的service中默认有一个分页查询的接口，请根据实际业务场景进行修改或删除
 *
 * 参考: mybatis-plus 3.5.1
 *
 *
 * @Author: mustang 
 * @Date: 2022/07/10 18:41
 */ 
public class Generator {
    /**
     * 数据库链接（只支持mysql）
     */
    private static String url = "jdbc:mysql://127.0.0.1:3306/db_user?serverTimezone=UTC";
    private static String user = "root";
    private static String pwd = "123456";


    public static void main(String[] args) throws SQLException {
        FastAutoGenerator.create(url,user,pwd)
                //全局设置
                .globalConfig(builder -> {
                    builder.author("mustang") //设置作者
                            .outputDir("G://")  //生成的文件输出目录
                            .tableName("t_user") //表名
                            .disableOpenDir() //禁止打开输出目录,如需生成代码后打开目录请删掉这行
                            .enableSwagger(true);  //开启swagger模式, false为关闭 ,默认为true
                })
                .packageConfig(builder -> {
                    builder.parent("top.mybi") //设置父包名
                         .moduleName("user"); //服务模块名：如user-svc就是user
                }).execute();
    }
}
