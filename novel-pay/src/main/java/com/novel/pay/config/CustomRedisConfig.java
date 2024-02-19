package com.novel.pay.config;


import com.novel.pay.centor.PayRedisCentor;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomRedisConfig {


    private final PayRedisCentor config;

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
