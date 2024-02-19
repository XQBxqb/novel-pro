package com.novel.user.centor;

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
public class DatasourceConfig {
    @Autowired
    private UserDatasoureCentor datasoureCentor;

    @Autowired
    private UserRedisConfigCentor userRedisConfigCentor;


    @Bean
    public DataSource dataSource() {
        System.out.println(datasoureCentor);
        System.out.println(userRedisConfigCentor);
        return DataSourceBuilder.create()
                .url(datasoureCentor.getUrl())
                .username(datasoureCentor.getUsername())
                .password(datasoureCentor.getPassword())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
