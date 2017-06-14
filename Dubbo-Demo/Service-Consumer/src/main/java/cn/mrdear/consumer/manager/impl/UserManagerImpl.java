package cn.mrdear.consumer.manager.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.stereotype.Service;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.client.service.IUserService;
import cn.mrdear.consumer.manager.UserManager;

import javax.annotation.Resource;

import java.util.Objects;

/**
 * manager调用远程RPC组装数据,此处调试只是再加一层封装
 * @author Niu Li
 * @since 2017/6/13
 */
@Service
public class UserManagerImpl implements UserManager {

  @Resource
  private IUserService userService;

  @Override
  @HystrixCommand(groupKey = "user", fallbackMethod = "findByIdBack")
  public UserDTO findById(Long id) {
    if (Objects.equals(id, 1L)) {
      try {
        Thread.sleep(1000000);
      } catch (InterruptedException e) {
        // do nothing
      }
    }
    if (Objects.equals(id, 2L)) {
      throw new RuntimeException("熔断测试");
    }
    return userService.findById(id);
  }

  public UserDTO findByIdBack(Long id) {
    System.err.println("findByIdBack");
    UserDTO userDTO = new UserDTO();
    userDTO.setAge(1);
    userDTO.setUsername("备用用户");
    return userDTO;
  }


}
