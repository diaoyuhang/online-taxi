package com.diao.rabbitmq.helloworld;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 17:00 2020/11/2
 */
//@Component
//@RabbitListener(queues = {"hello"})
public class Recv {

    @RabbitHandler
    public void handler(String mes) {
        System.out.println(mes);
    }
}
