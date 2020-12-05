package com.diao.eureka;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 14:30 2020/11/1
 */
public class TimeTaskTest {
    public static void main(String[] args){
        Timer time=new Timer("1");
    }
    @Test
    public void fun1(){
        Timer timer=new Timer("ww");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("abc");
            }
        };

        new Timer().schedule(task,1,1);

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
