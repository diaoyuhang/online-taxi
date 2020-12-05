package com.dian.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 14:09 2020/11/25
 */
@Component
public class UrlRouteFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String uri = requestContext.getRequest().getRequestURI();

        if (uri.startsWith("/api-passenger")) {
            //设置请求路径
            requestContext.put(FilterConstants.REQUEST_URI_KEY, "/lb/test2");
            //设置serviceId
            requestContext.put(FilterConstants.SERVICE_ID_KEY, "api-passenger");
        }
        return null;
    }
}
