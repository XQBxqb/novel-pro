package com.novel.book.amqp.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface DemoSource {
    @Output("erbadagang-output")
    MessageChannel erbadagangOutput();

}
