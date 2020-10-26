package com.diao.eureka.config;

import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 17:12 2020/10/25
 */
@Component
public class MyEventListener {

    @EventListener
    public void handleEurekaInstanceRegisteredEvent(EurekaInstanceRegisteredEvent event){
        System.out.println("eureka实例注册事件"+event);
    }
}
