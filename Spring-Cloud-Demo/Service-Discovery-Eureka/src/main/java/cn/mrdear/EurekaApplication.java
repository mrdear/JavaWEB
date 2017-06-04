package cn.mrdear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Niu Li
 * @since 2017/5/25
 */
@EnableEurekaServer//启动服务注册中心
@SpringBootApplication
public class EurekaApplication {
  public static void main(String[] args) {
    SpringApplication.run(EurekaApplication.class,args);
  }
}
