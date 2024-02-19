package com.novel.zuul.config;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulErrorFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPostFilter;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.filters.SentinelZuulPreFilter;
import com.netflix.zuul.ZuulFilter;
import org.springframework.context.annotation.Bean;

/**
 * @author 昴星
 * @date 2023-10-09 21:23
 * @explain
 */
public class ZuulConfig {
    /**
     * @description:预过滤器（Pre-Filter），在请求路由之前执行。
     * 它用于 Sentinel 的流量控制和请求属性的统计,10000是过滤器执行顺序，数字学校，优先级越高
     * @param
     * @return: com.netflix.zuul.ZuulFilter
     * @author: 昴星
     * @time: 2023-10-09 21:27*/
    @Bean
    public ZuulFilter sentinelZuulPreFilter() {
        return new SentinelZuulPreFilter(10000);
    }
    /**
     * @description:后过滤器（Post-Filter），在请求路由之后、响应返回给客户端之前执行
     * @param
     * @return: com.netflix.zuul.ZuulFilter
     * @author: 昴星
     * @time: 2023-10-09 21:27*/

    @Bean
    public ZuulFilter sentinelZuulPostFilter() {
        return new SentinelZuulPostFilter(1000);
    }
    /**
     * @description:错误过滤器（Error-Filter），用于处理在其他过滤器中抛出的异常。
     * 这里主要是用于 Sentinel 的熔断和降级逻辑。数字 -1 是该过滤器的执行顺序。
     * @param
     * @return: com.netflix.zuul.ZuulFilter
     * @author: 昴星
     * @time: 2023-10-09 21:27*/

    @Bean
    public ZuulFilter sentinelZuulErrorFilter() {
        return new SentinelZuulErrorFilter(-1);
    }
}