package com.haikong.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * 对于response的过滤器
 * 过滤器主要是用来操纵请求和响应参数像HTTP头，URI和/或HTTP方法
 * @author Niu Li
 * @date 2016/7/27
 */
@Provider
public class ResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext,
                       ContainerResponseContext containerResponseContext) throws IOException {
        /**
         * 具体可以获取什么参数,加个断点就可以看到了
         */
        System.out.println("执行回复过滤");
    }
}
