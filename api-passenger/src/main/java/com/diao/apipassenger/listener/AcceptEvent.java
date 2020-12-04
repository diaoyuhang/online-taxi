package com.diao.apipassenger.listener;

import com.alibaba.fastjson.JSONObject;
import com.diao.apipassenger.dao.EventOrderDao;
import com.diao.apipassenger.entity.EventOrder;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 10:33 2020/12/2
 */
@Component
public class AcceptEvent {

    @Resource
    private EventOrderDao eventOrderDao;

    @RabbitListener(queues = {"order_event"})
    @Transactional
    public void handler(Message msg, Channel channel) {
        try {
            EventOrder eventOrder = JSONObject.parseObject(new String(msg.getBody()), EventOrder.class);
            eventOrder.setState(3);
            int result = eventOrderDao.insertEventOrder(eventOrder);
//            int i = 1 / 0;
            System.out.println("==========" + msg);
        } catch (DuplicateKeyException e) {
            System.out.println("主键冲突，已经接受过这个消息！");
        } catch (Exception ex) {
            try {
                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
                throw new RuntimeException("消费失败!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
