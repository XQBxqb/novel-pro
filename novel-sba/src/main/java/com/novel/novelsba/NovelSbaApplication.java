package com.novel.novelsba;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class NovelSbaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NovelSbaApplication.class, args);
    }

}
