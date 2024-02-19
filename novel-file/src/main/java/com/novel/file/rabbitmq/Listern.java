package com.novel.file.rabbitmq;

import com.novel.file.consts.RabbitMQConsts;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 昴星
 * @date 2023-10-23 18:24
 * @explain
 */

@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitMQConsts.RABBITMQ_FILE_DIRECT_QUEUE_NAME))
public class Listern {
    @RabbitHandler
    public void mergeFile(Map map){
        System.out.println(map);
    }
}
