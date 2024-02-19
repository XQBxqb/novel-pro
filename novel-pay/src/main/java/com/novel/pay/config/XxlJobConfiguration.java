package com.novel.pay.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlJobConfiguration {


    @Value("${xxl.job.executor.appname}")
    private String appName;
    @Value("${xxl.job.admin.addresses}")
    private String address;
    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;
    @Value("${xxl.job.admin.accessToken}")
    private String accessToken;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        // 创建 XxlJobSpringExecutor 执行器
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(address);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        // 返回
        return xxlJobSpringExecutor;
    }

}