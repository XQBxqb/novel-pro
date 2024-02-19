package com.novel.book.centor;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 昴星
 * @date 2023-10-06 23:33
 * @explain
 */
@Component
@RefreshScope
@Data
public class BookElasticsearchCentor {
    @Value("${elastic.host}")
    private String host;

    @Value("${elastic.port}")
    private Integer port;

    @Value("${elastic.ssl.enabled}")
    private boolean sslEnabled;

    @Value("${elastic.ssl.keystore.path}")
    private String keystorePath;
}
