package com.diao.apipassenger.gray;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 16:34 2020/11/24
 */
//@Component
//@Aspect
public class RequestAspect {

    @Pointcut("execution(* com.diao.apipassenger.controller..*Controller*.*(..))")
    public void anyMethod() {
    }

    @Before("anyMethod()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String version = request.getHeader("version");
        HashMap<String, String> map = new HashMap<>();
        map.put("version", version);
        RibbonParameters.set(map);
    }
}
