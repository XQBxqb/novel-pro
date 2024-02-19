package com.novel.pay.config;


import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novel.pay.centor.PayElasticsearchCentor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 昴星
 * @date 2023-09-10 16:55
 * @explain
 */

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ElasticConfig {

    private final PayElasticsearchCentor centor;
    @Bean("restClient")
    RestClient restClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(centor.getHost(), centor.getPort()));
        return builder.build();
    }

    @Bean("getElasticSearchClient")
    public ElasticsearchClient getElasticSearchClient(@Qualifier("restClient") RestClient restClient) {
        return new ElasticsearchClient(new RestClientTransport(restClient, getJacksonJsonpMapper()));
    }

    @Bean("getElasticSearchAsyncClient")
    public ElasticsearchAsyncClient getElasticSearchAsyncClient(@Qualifier("restClient") RestClient restClient) {
        return new ElasticsearchAsyncClient(new RestClientTransport(restClient, getJacksonJsonpMapper()));
    }

    private static JacksonJsonpMapper getJacksonJsonpMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.registerModule(new JavaTimeModule());
        JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);
        return jsonpMapper;
    }

}
