package com.novel.pay.amqp;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@EnableBinding(MySink.class)
@Component
public class Demo01Consumer {

    @StreamListener(MySink.ERBADAGANG_INPUT)
    public void onMessage(String message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

    @StreamListener(MySink.TREK_INPUT)
    public void onTrekMessage(String message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
