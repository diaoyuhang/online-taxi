package com.dian.zuul.filter;

import com.dian.zuul.util.JwtInfo;
import com.dian.zuul.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Mr.diao
 * @Description:
 * @Date: 9:47 2020/11/10
 */
//@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestURI = request.getRequestURI();
        //如果请求是获取token，则不执行过滤器
        if (requestURI.startsWith("/login"))
            return false;

        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String access_token = request.getHeader("access_token");
        JwtInfo jwtInfo = null;

        if (access_token != null && !"".equals(access_token)) {
            jwtInfo = JwtUtil.parseToken(access_token);
        }
        if (jwtInfo == null) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            currentContext.setResponseBody("auth fail");
        }

        return null;
    }
}
