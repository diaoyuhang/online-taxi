package com.dian.zuul.controller;

import com.dian.zuul.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:42 2020/11/10
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/getToken")
    public String getToken(String username){

        String token = JwtUtil.createToken(username, new Date());

        return token;
    }
}
