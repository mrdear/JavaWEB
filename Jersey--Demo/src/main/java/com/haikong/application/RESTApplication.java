package com.haikong.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.haikong.filter.PreRequestFilter;
import com.haikong.filter.ResponseFilter;
import com.haikong.interceptor.GzipInterceptor;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Niu Li
 * @date 2016/7/25
 */
public class RESTApplication extends ResourceConfig {

    public RESTApplication() {

        //想让jersey托管的部分需要加入扫描,或者使用register指定托管类也可以
        packages("com.haikong.resources","com.haikong.exception");
        register(LoggingFilter.class);
        //json支持
        register(JacksonJsonProvider.class);
        //xml支持
//        register(MoxyXmlFeature.class);
        //文件上传支持
//        register(MultiPartFeature.class);
        //注册拦截器
        register(GzipInterceptor.class);
        //注册过滤器,扫包对@PreMatching注解无用,只能手动加入
        register(PreRequestFilter.class);
        register(ResponseFilter.class);
        /**
         * 对于链接,先执行请求过滤,有异常则执行异常过滤,最后执行回复过滤
         */
        System.out.println("加载RESTApplication");
    }
}
