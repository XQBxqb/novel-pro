package com.novel.pay.centor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 昴星
 * @date 2023-09-04 17:07
 * @explain
 */

@Component
@RefreshScope
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class PayRedisCentor {

    private String host;

    private String password;

    private String port;
}
