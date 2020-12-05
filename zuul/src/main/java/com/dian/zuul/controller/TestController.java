package com.dian.zuul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:34 2020/11/25
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/myController")
    public String fun1(){
        return "fun1";
    }
}
