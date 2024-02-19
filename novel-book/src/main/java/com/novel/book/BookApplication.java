package com.novel.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 昴星
 * @date 2023-09-07 21:21
 * @explain
 */

@SpringBootApplication(scanBasePackages = {"com.novel"})
@MapperScan(basePackages = {"com.novel.book.dao"})
@EnableDiscoveryClient
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class,args);
    }
}
