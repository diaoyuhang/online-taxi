package com.diao.rabbitmq.helloworld;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 16:52 2020/11/2
 */
@Component
public class Send {
    private static final String QUEUE_NAME = "hello";
    @Resource
    RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 10000, initialDelay = 10)
    public void send() {
        String message = "Hello world";
        rabbitTemplate.convertAndSend(QUEUE_NAME,message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
