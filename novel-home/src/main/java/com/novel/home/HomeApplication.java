package com.novel.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 昴星
 * @date 2023-10-04 17:31
 * @explain
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.novel.home.dao"})
@EnableDiscoveryClient
public class HomeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeApplication.class,args);
    }
}
