package com.novel.file.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.STSAssumeRoleSessionCredentialsProvider;
import com.aliyuncs.exceptions.ClientException;
import com.novel.file.centor.FileOSSCentor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author 昴星
 * @date 2023-10-21 16:46
 * @explain
 */
@Configuration
@RequiredArgsConstructor
public class OSSConfig {

    private final FileOSSCentor fileOSSCentor;
    //使用STSAssume配置
    /*@Bean("getCredentials")
    public STSAssumeRoleSessionCredentialsProvider stsAssumeRoleSessionCredentialsProvider() throws ClientException {
        String region = fileOSSCentor.getRegion();
        String accessKeyId = fileOSSCentor.getAccessKeyId();
        String accessKeySecret = fileOSSCentor.getAccessKeySecret();
        String roleArn = fileOSSCentor.getRoleArn();
        STSAssumeRoleSessionCredentialsProvider credentialsProvider = CredentialsProviderFactory
                .newSTSAssumeRoleSessionCredentialsProvider(
                        region,
                        accessKeyId,
                        accessKeySecret,
                        roleArn
                );
        return credentialsProvider;
    }*/

    @Bean
    public OSS oss(){
        String endpoint = fileOSSCentor.getEndpoint();
        //下面是ossClient的连接，文件传输配置
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setMaxConnections(200);
        conf.setSocketTimeout(10000);
        conf.setConnectionTimeout(10000);
        conf.setConnectionRequestTimeout(1000);
        conf.setIdleConnectionTime(10000);
        conf.setMaxErrorRetry(5);
        return new OSSClientBuilder().build(endpoint, fileOSSCentor.getAccessKeyId(),fileOSSCentor.getAccessKeySecret(),conf);
    }
}
