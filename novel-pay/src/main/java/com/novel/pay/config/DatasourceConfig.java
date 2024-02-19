package com.novel.pay.config;


import com.novel.pay.centor.PayDatasourceCentor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author 昴星
 * @date 2023-10-06 14:08
 * @explain
 */
@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

    private final PayDatasourceCentor datasoureCentor;

    @Bean
    public DataSource dataSource() {
        System.out.println(datasoureCentor);
        return DataSourceBuilder.create()
                                .url(datasoureCentor.getUrl())
                                .username(datasoureCentor.getUsername())
                                .password(datasoureCentor.getPassword())
                                .driverClassName("com.mysql.cj.jdbc.Driver")
                                .build();
    }
}
