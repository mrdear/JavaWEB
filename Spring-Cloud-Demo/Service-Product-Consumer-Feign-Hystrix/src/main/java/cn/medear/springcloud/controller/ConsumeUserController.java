package cn.medear.springcloud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import cn.medear.springcloud.entity.User;
import cn.medear.springcloud.restclient.UserClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于消费UserService的类
 * @author Niu Li
 * @since 2017/6/3
 */
@RestController
@Slf4j
public class ConsumeUserController {

  @Resource
  private UserClient userClient;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @GetMapping("/{id}")
  public String findUserById(@PathVariable Long id) {
    User user = userClient.findById(id);
    String valueAsString = null;
    try {
      valueAsString = objectMapper.writeValueAsString(user);
    } catch (JsonProcessingException e) {
      log.error("parse json error. {}",e);
    }
    return valueAsString;
  }
}
