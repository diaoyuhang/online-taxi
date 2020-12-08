package com.diao.apipassenger.gray;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 16:28 2020/11/24
 */
//@Configuration
public class GrayRibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new GrayRule();
    }
}
