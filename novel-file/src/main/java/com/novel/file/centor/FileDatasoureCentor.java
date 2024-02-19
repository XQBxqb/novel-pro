package com.novel.file.centor;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class FileDatasoureCentor {

    private String url;

    private String username;

    private String password;
}
