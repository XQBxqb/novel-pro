package com.novel.file.centor;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class FileRedisConfigCentor {
    private String host;

    private String password;

    private String port;
}
