package cn.mrdear.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import cn.mrdear.entity.User;
import cn.mrdear.resporitory.UserResporitory;

/**
 * @author Niu Li
 * @since 2017/5/25
 */
@RestController
public class UserController {

  @Value("${spring.application.name}")
  private String serverName;

  @Resource
  private UserResporitory userResporitory;

  @Resource
  private DiscoveryClient discoveryClient;

  @RequestMapping(value = "/add" ,method = RequestMethod.GET)
  public User add(String username,String password) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    userResporitory.saveAndFlush(user);
    return user;
  }

  @GetMapping("/{id}")
  public User find(@PathVariable Long id) {
    return userResporitory.findOne(id);
  }

  @GetMapping("/list")
  public List<User> findAll(){
    return userResporitory.findAll();
  }

  @GetMapping("/console")
  public List<ServiceInstance> console(){
    List<ServiceInstance> instances = discoveryClient.getInstances(serverName);
    return instances;
  }

}
