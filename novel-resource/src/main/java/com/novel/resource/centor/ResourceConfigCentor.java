package com.novel.resource.centor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 昴星
 * @date 2023-08-17 14:52
 * @explain
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class ResourceConfigCentor {

    public String password;

    public String host;

    public String port;
}
