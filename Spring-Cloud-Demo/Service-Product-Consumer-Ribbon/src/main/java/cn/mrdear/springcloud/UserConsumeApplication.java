package cn.mrdear.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Niu Li
 * @since 2017/6/3
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserConsumeApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserConsumeApplication.class,args);
  }

}
