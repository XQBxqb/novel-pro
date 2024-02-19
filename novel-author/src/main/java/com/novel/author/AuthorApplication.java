package com.novel.author;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 昴星
 * @date 2023-10-12 20:10
 * @explain
 */
@SpringBootApplication
@MapperScan("cn.novel.author.dao")
@EnableFeignClients(basePackages = "cn.novel.author.feign")
public class AuthorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorApplication.class,args);
    }
}
