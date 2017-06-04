import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;


/**
 * @author Niu Li
 * @since 2017/6/3
 */
@SpringBootApplication(scanBasePackages = "cn.medear.springcloud.controller")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.medear.springcloud.restclient")
public class UserConsumeFeignApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserConsumeFeignApplication.class, args);
  }

  /**
   * 自定义feign的Httpclient,其注入在HttpClientFeignLoadBalancedConfiguration中
   * @return
   */
  @Bean
  public HttpClient feignHttpClient() {
    PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER =
        new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
    HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
    HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER)
        .setDefaultRequestConfig(requestConfig).build();
    return httpClient;
  }
}
