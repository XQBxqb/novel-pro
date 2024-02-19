package com.novel.user.centor.centor;

import com.novel.user.centor.UserRedisConfigCentor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 昴星
 * @date 2023-10-06 13:03
 * @explain
 */
@Configuration
public class CusRedisConfig {
    @Autowired
    private UserRedisConfigCentor config;

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(config.getHost());
        redisConfig.setPassword(config.getPassword());
        redisConfig.setPort(Integer.parseInt(config.getPort()));
        redisConfig.setDatabase(0);
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
