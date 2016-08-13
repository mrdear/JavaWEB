package cn.mrdear.conf;

import net.sf.ehcache.constructs.web.filter.GzipFilter;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 定义一些Filter使用
 * @author Niu Li
 * @date 2016/8/13
 */
@Configuration
public class FilterConfig {
    /**
     * 配置ehcache的Gzip压缩
     * @return
     */
    @Bean
    public FilterRegistrationBean gzipFilter(){
        FilterRegistrationBean gzipFilter = new FilterRegistrationBean(new GzipFilter());
        String[] arrs = {"*.js","*.css","*.json","*.html"};
        gzipFilter.setUrlPatterns(Arrays.asList(arrs));
        return gzipFilter;
    }
    /**
     * 配置页面缓存,页面缓存会自动开启GZIP压缩
     */
    @Bean
    public FilterRegistrationBean helloFilter(){
        FilterRegistrationBean helloFilter = new FilterRegistrationBean(new SimplePageCachingFilter());
        Map<String,String> maps = new HashMap<>();
        //设置参数
        maps.put("cacheName","hello");
        helloFilter.setInitParameters(maps);
        //设置路径
        String[] arrs = {"/hello"};
        helloFilter.setUrlPatterns(Arrays.asList(arrs));
        return helloFilter;
    }

}
