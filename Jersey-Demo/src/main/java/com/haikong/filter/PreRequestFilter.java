package com.haikong.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import java.io.IOException;

/**
 * 对于request的过滤器
 * 过滤器主要是用来操纵请求和响应参数像HTTP头，URI和/或HTTP方法
 * @author Niu Li
 * @date 2016/7/27
 * Provider //这个是匹配后增加参数或者减少参数
 */
@PreMatching  //不知道为什么和后请求过滤器冲突,不能同时使用
public class PreRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        /**
         * 具体可以获取什么参数,加个断点就可以看到了
         */
        System.out.println("PreRequestFilter");
    }
}
