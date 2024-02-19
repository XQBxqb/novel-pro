package com.novel.user.centor;

/**
 * @author 昴星
 * @date 2023-09-21 20:14
 * @explain
 */

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@AutoConfigureBefore(RedissonAutoConfiguration.class)
public class RedissonConfig {
    @Autowired
    private UserRedisConfigCentor redisConfigCentor;

    @Bean
    public Redisson redisson(){
        System.out.println(redisConfigCentor);
        Config config = new Config();
        config.setCodec(new StringCodec());
        config.useSingleServer().
                setAddress("redis://"+redisConfigCentor.getHost()+":"+redisConfigCentor.getPort()).
                setPassword(redisConfigCentor.getPassword());
        return (Redisson) Redisson.create(config);
    }
}
