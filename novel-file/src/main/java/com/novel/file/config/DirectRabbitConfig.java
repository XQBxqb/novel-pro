package com.novel.file.config;


import com.novel.file.consts.RabbitMQConsts;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;


/**
 * @author 昴星
 * @date 2023-10-23 18:02
 * @explain
 */
@Configuration
public class DirectRabbitConfig implements BeanPostProcessor {

    /*@Value("${spring.rabbitmq.address}")
    private String address;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${mq.virtualhost}")
    private String virtualhost;
    //这是创建交换机和队列用的rabbitAdmin对象
    @Autowired
    private RabbitAdmin rabbitAdmin;*/


    /*@Bean("getConnectionFactory")
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        return connectionFactory();
    }*/




    //初始化rabbitAdmin对象
    /*@Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("getConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }*/

    //实例化bean后，也就是Bean的后置处理器
   /* @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //创建交换机
        rabbitAdmin.declareExchange(rabbitDirectExchange());
        //创建队列
        rabbitAdmin.declareQueue(rabbitDirectQueue());
        return null;
    }*/
/*
    @Bean
    public Queue rabbitDirectQueue(){
        return new Queue(RabbitMQConsts.RABBITMQ_FILE_DIRECT_QUEUE_NAME,true,false,false);
    }
    @Bean
    public DirectExchange rabbitDirectExchange(){
        return new DirectExchange(RabbitMQConsts.RABBITMA_FILE_DIRECT_EXCHANGE,true,false);
    }
    @Bean
    public Binding bindDirect(){
        return BindingBuilder.bind(rabbitDirectQueue()).to(rabbitDirectExchange()).with(RabbitMQConsts.RABBITMQ_FILE_BINDING_KEY);
    }*/
}
