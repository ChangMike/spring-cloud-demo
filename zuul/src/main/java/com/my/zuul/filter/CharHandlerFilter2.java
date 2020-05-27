package com.my.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CharHandlerFilter2 extends ZuulFilter {

    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        // 获取上一个拦截器设置的状态
        RequestContext currentContext = RequestContext.getCurrentContext();
        boolean b = currentContext.sendZuulResponse();
        return b;
    }

    public Object run() throws ZuulException {
        System.err.println("-----------------经过了过滤器2-----------------");
        return null;
    }
}
