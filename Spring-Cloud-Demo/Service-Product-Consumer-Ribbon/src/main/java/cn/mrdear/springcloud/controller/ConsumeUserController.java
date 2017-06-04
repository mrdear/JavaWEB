package cn.mrdear.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 用于消费UserService的类
 * @author Niu Li
 * @since 2017/6/3
 */
@RestController
public class ConsumeUserController {

  @Resource
  private RestTemplate restTemplate;

  @GetMapping("/{id}")
  public String findUserById(@PathVariable Long id) {
    return restTemplate.getForObject("http://service-user-provider/"+id,String.class);
  }
}
