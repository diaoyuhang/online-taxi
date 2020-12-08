package com.diao.apipassenger.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:12 2020/12/4
 */
@FeignClient(name = "service-sms", fallback = TestClientImpl.class)
public interface TestClient {

    @GetMapping(value = "/test/testLB")
    public String test();
}
