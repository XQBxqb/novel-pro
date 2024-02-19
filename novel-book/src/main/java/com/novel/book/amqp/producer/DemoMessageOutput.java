package com.novel.book.amqp.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(DemoSource.class)
public class DemoMessageOutput {
    @Autowired
    private DemoSource demoSource;

    @GetMapping("/send")
    public boolean send(){
        Message<String> message = MessageBuilder.withPayload("Hello rocketMq").build();
        return demoSource.erbadagangOutput().send(message);
    }
}
