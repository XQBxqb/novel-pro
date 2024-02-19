package com.novel.file.centor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author 昴星
 * @date 2023-10-21 16:49
 * @explain
 */
@Component
@RefreshScope
@Data
@ConfigurationProperties(prefix = "alibaba.oss")
public class FileOSSCentor {
    private String region;

    private String accessKeyId;

    private String accessKeySecret;

    private String roleArn;

    private String endpoint;

    private String bucketName;
}
