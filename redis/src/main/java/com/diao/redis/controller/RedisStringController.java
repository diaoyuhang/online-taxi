package com.diao.redis.controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 14:47 2020/10/27
 */
@RestController
@RequestMapping("/redisString")
public class RedisStringController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/set")
    public Object set(String key,String value){
        //设置有时间限制的key
//        stringRedisTemplate.opsForValue().set(key,value,10, TimeUnit.SECONDS);
        //key不存在时，才能设置成功
        stringRedisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(),value.getBytes());
        return null;
    }

    @GetMapping("/setRange")
    public Object setRange(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value,2);
        return null;
    }
}
