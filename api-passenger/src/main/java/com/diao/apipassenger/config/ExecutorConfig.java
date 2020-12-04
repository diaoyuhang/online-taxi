package com.diao.apipassenger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 16:43 2020/11/9
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean
    public Executor asyncServiceExecutor() {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4,
                12,
                5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }
}
