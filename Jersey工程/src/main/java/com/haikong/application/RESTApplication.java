package com.haikong.application;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Niu Li
 * @date 2016/7/25
 */
public class RESTApplication extends ResourceConfig {

    public RESTApplication() {

        packages("com.haikong.resources;com.haikong.exception");
        register(LoggingFilter.class);
        register(JacksonJsonProvider.class);

        System.out.println("加载RESTApplication");
    }
}
