package com.diaoi.servicesms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 10:12 2020/11/5
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/testLB")
    public String test() {
        int i = 1 / 0;
        System.out.println(port);
        return "success:" + port;
    }
}
