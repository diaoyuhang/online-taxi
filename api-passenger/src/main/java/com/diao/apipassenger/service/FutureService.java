package com.diao.apipassenger.service;

import com.diao.apipassenger.entity.Shopping;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 17:19 2020/11/9
 */
@Service
public class FutureService {
    @Resource(name = "asyncServiceExecutor")
    private Executor executor;

    public Shopping getShopping() {
        Callable<Shopping> callable = new Callable<>() {
            @Override
            public Shopping call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(5000);
                int i = 1 / 0;
                return new Shopping("computer", 12.0);
            }
        };

        FutureTask<Shopping> task = new FutureTask<>(callable);
        executor.execute(task);

        if (!task.isDone()) {
            System.out.println("任务还没执行");
        }
        try {
            Thread.sleep(4000);//模拟其他操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Shopping shopping = null;
        try {
            shopping = task.get();//这个阻塞的方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return shopping;
    }

}

