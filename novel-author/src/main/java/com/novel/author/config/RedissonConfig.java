package com.novel.author.config;

/**
 * @author 昴星
 * @date 2023-09-21 20:14
 * @explain
 */

import org.redisson.Redisson;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private String redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.setCodec(new StringCodec());
        config.useSingleServer().
                setAddress("redis://"+redisHost+":"+redisPort).
                setPassword(redisPassword);
        return (Redisson) Redisson.create(config);
    }
}
