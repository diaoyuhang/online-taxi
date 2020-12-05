package com.diao.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:50 2020/11/2
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setUsername("diao");
        connectionFactory.setPassword("diao");
        connectionFactory.setVirtualHost("/diao");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            String mes = new String(message.getBody());
            System.out.println(mes);
        }, (consumerTag) -> {
        });

//        channel.close();
//        connection.close();
    }
}
