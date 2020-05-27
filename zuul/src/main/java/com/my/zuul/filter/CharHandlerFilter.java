package com.my.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@Component
@RefreshScope
public class CharHandlerFilter extends ZuulFilter {
    // 注入远程配置
    @Value("${token}")
    private String myToken;

    // 过滤器类型，有pre、route、post及error
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    // 调用顺序，可以是负数，同类型的过滤器，此值越小越早执行
    public int filterOrder() {
        return 0;
    }
    // 是否启用些拦截器
    public boolean shouldFilter() {
        return true;
    }
    // 拦截器逻辑，返回值没有用，不需要关注
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 可以在currentContext上设置自定义的值
        currentContext.set("country", "china");
        // 可以获得request、response对象，就像原生的JavaEE过滤器一样，可以添加字符替换和编码替换等功能
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        /**
         *  一个测试
         *  使用浏览器测试把下面这一行注释放开
         *  使用远程配置注释下面这一行
         */
        // String myToken = request.getHeader("myToken");
        if (myToken != null && myToken.equals("1")) {
            // 不能继续访问
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            currentContext.setResponseBody("{'msg':'my filter error'}");
        }
        return null;
    }
}
