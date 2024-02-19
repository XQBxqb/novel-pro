package com.novel.file.config;

/**
 * @author 昴星
 * @date 2023-09-21 20:14
 * @explain
 */


import com.novel.file.centor.FileRedisConfigCentor;


import org.redisson.Redisson;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
   @Autowired
   private FileRedisConfigCentor redisCentor;

    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.setCodec(new StringCodec());
        config.useSingleServer().
                setAddress("redis://"+redisCentor.getHost()+":"+redisCentor.getPort()).
                setPassword(redisCentor.getPassword());
        return (Redisson) Redisson.create(config);
    }
}
