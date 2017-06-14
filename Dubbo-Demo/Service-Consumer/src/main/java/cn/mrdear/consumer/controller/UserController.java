package cn.mrdear.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.consumer.manager.UserManager;

import javax.annotation.Resource;

/**
 * @author Niu Li
 * @since 2017/6/14
 */
@RestController
public class UserController {

  @Resource
  private UserManager userManager;

  @GetMapping("/user/{id}")
  public UserDTO getUserById(@PathVariable Long id) {
    UserDTO userDTO = userManager.findById(id);
    return userDTO;
  }
}
