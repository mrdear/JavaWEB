package cn.mrdear.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Niu Li
 * @since 2017/6/3
 */
@Configuration
//@RibbonClient(name = "user-resporitory",configuration = MyConfig.class)
//可以指定针对某服务器采取指定的配置负载均衡算法,配置文件中配置更加方便
public class BeanConfig {

  @Bean
  @LoadBalanced //开启客户端负载均衡,自动配置类在LoadBalancerAutoConfiguration中
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
