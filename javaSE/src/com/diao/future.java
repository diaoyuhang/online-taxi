package com.diao;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 16:00 2020/11/9
 */
public class future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        Callable<Shopping> callable = new Callable<>() {
            @Override
            public Shopping call() throws Exception {
                Thread.sleep(5000);
                return new Shopping("computer", 12.0);
            }
        };

        FutureTask<Shopping> task = new FutureTask<>(callable);
        new Thread(task).start();

        if (!task.isDone()) {
            System.out.println("任务还没执行");
        }
        Thread.sleep(4000);//模拟其他操作

        Shopping shopping = task.get();//这个阻塞的方法
        System.out.println(shopping);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}

class Shopping {

    private String name;
    private Double price;

    public Shopping(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}