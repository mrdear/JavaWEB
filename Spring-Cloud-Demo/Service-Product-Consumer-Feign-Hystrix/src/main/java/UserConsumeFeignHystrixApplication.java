import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * @author Niu Li
 * @since 2017/6/3
 */
@SpringBootApplication(scanBasePackages = "cn.medear.springcloud")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.medear.springcloud.restclient")
public class UserConsumeFeignHystrixApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserConsumeFeignHystrixApplication.class, args);
  }
}
