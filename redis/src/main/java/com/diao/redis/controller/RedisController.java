package com.diao.redis.controller;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 11:28 2020/10/26
 */
@RestController
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "user2")
    private User user;

    @Resource
    private BeanFactory beanFactory;

    private RateLimiter rateLimiter = RateLimiter.create(5);

    @GetMapping("/limiting")
    public Boolean limiting() {
//        beanFactory.getBean("user2");
//        beanFactory.getBean(User.class);
        System.out.println(user);

        Boolean flag = false;
        if (rateLimiter.tryAcquire()) {
            flag = true;
        }
        System.out.println(flag);
        return flag;
    }


    @GetMapping("/saveString")
    public Object saveString(String key, String value) {

//        Integer append = redisTemplate.opsForValue().append(key, value);

        redisTemplate.opsForValue().setBit("mybit", 5, true);
        Object mybit = redisTemplate.opsForValue().getBit("mybit", 5);
        System.out.println(mybit);
        return null;
    }

    @GetMapping("/saveList")
    public Object saveList() {
//        redisTemplate.boundListOps("myList").rightPush("one");
        ListOperations listOperations = redisTemplate.opsForList();
//        listOperations.rightPush("myList","two");
        String value = (String) listOperations.rightPop("myList");
        return value;
    }

    @GetMapping("/saveSet")
    public Object saveSet() {
        BoundSetOperations mySet = redisTemplate.boundSetOps("mySet");
//        Long add = mySet.add(1, 2, 3, 4, 5, 1, 2, 3, 4);
        Integer num = (Integer) mySet.pop();
//        return "总添加数量：" + add;
        return "取出得数：" + num;
    }

    @GetMapping("/saveHashMap")
    public Object saveHashMap() {
        BoundHashOperations myHashSet = redisTemplate.boundHashOps("myHashSet");
//        myHashSet.put("one","1");
//        myHashSet.put("two","2");
//        myHashSet.put("three","3");
        String one = (String) myHashSet.get("two");
        return one;
    }
}