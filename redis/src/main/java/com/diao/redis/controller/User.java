package com.diao.redis.controller;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 14:16 2020/10/26
 */
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
