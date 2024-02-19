package com.novel.book.amqp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@EnableBinding(MySink.class)
@Component
public class Demo01Consumer {

    @StreamListener(MySink.ERBADAGANG_INPUT)
    public void onMessage(String message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
