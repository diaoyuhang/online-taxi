package com.diao.apipassenger.gray;

import org.springframework.stereotype.Component;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 15:39 2020/11/24
 */
//@Component
public class RibbonParameters {

    public static final ThreadLocal local=new ThreadLocal();

    public static <T> T get(){
        return (T) local.get();
    }

    public static <T> void set(T t){
        local.set(t);
    }
}
