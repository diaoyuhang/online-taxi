package com.diao.redis.service;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 13:53 2020/11/4
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate redisTemplate;

    public String generateCapthca(String num) {
        BoundValueOperations<Object,Long> ops = redisTemplate.boundValueOps(num);
        Long s = ops.get();
        if (s == null) {
            ops.set(1l, 60, TimeUnit.SECONDS);
        } else if (s < 4) {
            ops.increment(1);
        } else {
            return "发送频繁";
        }
        //根据num生成redis身份key
        int identity = hash(num);
        BoundValueOperations codeOps = redisTemplate.boundValueOps(identity);

        //生成验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * Math.pow(10, 5)));
        codeOps.set(code,5*60,TimeUnit.SECONDS);


        return code;
    }

    private int hash(String num) {

        return num.hashCode();
    }

}
