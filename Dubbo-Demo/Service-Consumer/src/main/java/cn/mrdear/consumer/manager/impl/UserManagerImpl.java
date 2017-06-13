package cn.mrdear.consumer.manager.impl;

import org.springframework.stereotype.Service;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.client.service.IUserService;
import cn.mrdear.consumer.manager.UserManager;

import javax.annotation.Resource;

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
  public UserDTO findById(Long id) {
    return userService.findById(id);
  }
}
