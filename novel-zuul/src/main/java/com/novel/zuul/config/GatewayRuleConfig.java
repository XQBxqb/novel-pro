package com.novel.zuul.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.property.DynamicSentinelProperty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.novel.api.exception.BusinessException;
import com.novel.core.enums.ErrorStatusEnums;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author 昴星
 * @date 2023-10-09 21:29
 * @explain
 */
//@Configuration
public class GatewayRuleConfig {
    @Value("${sentinel.api-definition.address}")
    private String apiAddr;

    @Value("${sentinel.api-definition.data-id}")
    private String apiApplication;

    @Value("${sentinel.gatewayrule-manager.address}")
    private String getewayAddr;

    @Value("${sentinel.gatewayrule-manager.data-id}")
    private String getewayApplication;

//    @PostConstruct
    public void doInit() {
        //仅在测试时使用的dashboard
        /*System.setProperty("csp.sentinel.dashboard.server", "localhost:8080");
        System.setProperty("csp.sentinel.api.port", "8719");*/
        initApiDefinetions();
        initGatewayRules();
    }
    /**
     * @description:sentinel从nacos配置中心动态读取ApiDefinetion配置
     * @param
     * @return: void
     * @author: 昴星
     * @time: 2023-10-09 22:56
     */
    private void initApiDefinetions() {
        // Nacos 服务器地址
        String serverAddr = apiAddr;
        // Nacos 配置的 DataId
        String dataId = apiApplication;
        // Nacos 配置的 Group
        String groupId = "DEFAULT_GROUP";

        // 1. 创建 NacosDataSource
        NacosDataSource<Set<ApiDefinition>> nacosDataSource = new NacosDataSource<>(serverAddr, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<Set<ApiDefinition>>() {}));

        // 2. 创建 DynamicSentinelProperty 并从 NacosDataSource 获取属性值
        DynamicSentinelProperty<Set<ApiDefinition>> property = new DynamicSentinelProperty<>();
        try {
            property.updateValue(nacosDataSource.loadConfig());
        } catch (Exception e) {
            throw new BusinessException(ErrorStatusEnums.RES_ES_ERR);
        }

        String string = property.toString();
        System.out.println(string);
        // 3. 注册属性到 GatewayApiDefinitionManager
        GatewayApiDefinitionManager.register2Property(property);
    }
    /**
     * @description:sentinel从nacos配置中心获取GatewayRuleManager配置
     * @param
     * @return: void
     * @author: 昴星
     * @time: 2023-10-09 22:57*/

    private void initGatewayRules() {
        String serverAddr = getewayAddr;
        String dataId = getewayApplication;
        String groupId = "DEFAULT_GROUP";

        ReadableDataSource<String, Set<GatewayFlowRule>> gatewayRuleNacosDataSource = new NacosDataSource<>(serverAddr, groupId, dataId,
                source -> JSON.parseObject(source, new TypeReference<Set<GatewayFlowRule>>() {}));
        DynamicSentinelProperty<Set<GatewayFlowRule>> property = new DynamicSentinelProperty<>();
        try {
            property.updateValue(gatewayRuleNacosDataSource.loadConfig());
        } catch (Exception e) {
            throw new BusinessException(ErrorStatusEnums.RES_ES_ERR);
        }
        GatewayRuleManager.register2Property(gatewayRuleNacosDataSource.getProperty());
    }
}