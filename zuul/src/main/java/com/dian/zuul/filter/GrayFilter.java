package com.dian.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:41 2020/11/17
 */
//@Component
public class GrayFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String userId = request.getHeader("userId");
        if ("1".equals(userId)) {
            RibbonFilterContextHolder.getCurrentContext().add("version", "v3");
        } else if("2".equals(userId)){
            RibbonFilterContextHolder.getCurrentContext().add("version", "v2");
        }
        return null;
    }
}
