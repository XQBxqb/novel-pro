package com.novel.home.centor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 昴星
 * @date 2023-09-04 17:22
 * @explain
 */

@Component
@RefreshScope
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DatasourceConfig {
    private String url;

    private String username;

    private String password;
}
