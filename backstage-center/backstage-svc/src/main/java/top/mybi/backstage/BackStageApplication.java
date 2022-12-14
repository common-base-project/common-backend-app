package top.mybi.backstage;

import top.mybi.common.feign.annotation.EnableFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Description: 后台管理系统程序主入口
 *
 * @Author: mustang
 * @Date: 2022/7/11 14:05
 */
@SpringBootApplication
@EnableFeign
public class BackStageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class,args);
    }
}
