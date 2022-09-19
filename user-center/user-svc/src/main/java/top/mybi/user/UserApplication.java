package top.mybi.user;


import top.mybi.common.feign.annotation.EnableFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户中心服务
 * @Author mustang
 */
@EnableFeign
@SpringBootApplication(scanBasePackages = {"top.mybi.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
