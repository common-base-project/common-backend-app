package com.sipue.user;


import com.sipue.common.feign.annotation.EnableFeign;
import com.sipue.common.job.annotation.EnableSipueXxlJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户中心服务
 * @Author wangjunyu
 */
@EnableSipueXxlJob
@EnableFeign
@SpringBootApplication(scanBasePackages = {"com.sipue.user"})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
