package com.diao.apipassenger.controller;

import com.diao.apipassenger.entity.Shopping;
import com.diao.apipassenger.service.FutureService;
import com.diao.apipassenger.service.TestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 10:14 2020/11/5
 */
@RestController
@RequestMapping("/lb")
public class TestController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private FutureService futureService;

    @Resource
    private TestClient testClient;

    @GetMapping("/test")
    public String test() {
//        String url = "http://service-sms/test/testLB";
//        String result = restTemplate.getForObject(url, String.class);
        String result = testClient.test();
        return result;
    }

    @GetMapping("/test2")
    public String test2(String name) {
        System.out.println(name);
        return "test2";
    }

    @GetMapping("/testFuture")
    public Shopping testFuture() {
        return futureService.getShopping();
    }
}
