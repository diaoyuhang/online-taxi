package com.diao.rabbitmq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:31 2020/11/2
 */
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("/diao");
        connectionFactory.setUsername("diao");
        connectionFactory.setPassword("diao");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //申明一个队列，只有当他不存在的时候才会创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //exchange 交换机不能为null
        channel.basicPublish("",QUEUE_NAME,null,"hello".getBytes());

        channel.close();
        connection.close();
    }
}
