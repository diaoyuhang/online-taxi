package com.diao.apipassenger.service;

import org.springframework.stereotype.Component;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:17 2020/12/4
 */
@Component
public class TestClientImpl implements TestClient{
    @Override
    public String test() {
        System.out.println("执行降级策略");
        return null;
    }
}
