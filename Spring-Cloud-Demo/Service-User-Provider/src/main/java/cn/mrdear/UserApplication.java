package cn.mrdear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Niu Li
 * @since 2017/5/25
 */
@EnableDiscoveryClient
@SpringBootApplication
public class UserApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class,args);
  }

}
