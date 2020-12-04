package com.diao.apipassenger.cron;

import com.alibaba.fastjson.JSONObject;
import com.diao.apipassenger.dao.EventPayDao;
import com.diao.apipassenger.entity.EventPay;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:59 2020/12/1
 */
@Component
public class PublishEvent {
    private static final String QUEUE_NAME = "order_event";
    @Resource
    private EventPayDao eventPayDao;

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publish() {
        List<EventPay> eventPays = eventPayDao.selectByState(1);
        eventPays.forEach((eventPay) -> {
            eventPay.setState(2);
            eventPayDao.updateByPrimaryKeySelective(eventPay);
            rabbitTemplate.convertAndSend(QUEUE_NAME, JSONObject.toJSONString(eventPay));
//            throw new RuntimeException("模拟发送失败！");
        });
    }

}
