package com.novel.user;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 昴星
 * @date 2023-09-03 21:48
 * @explain
 */

@SpringBootApplication(scanBasePackages = "com.novel")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.novel.user.feign")
@MapperScan(basePackages = "com.novel.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
