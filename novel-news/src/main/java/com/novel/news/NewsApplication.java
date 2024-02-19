package com.novel.news;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 昴星
 * @date 2023-10-04 18:49
 * @explain
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.novel.news.dao"})
@EnableDiscoveryClient
public class NewsApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class,args);
    }
}
