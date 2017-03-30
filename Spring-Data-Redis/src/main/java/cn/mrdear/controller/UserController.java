package cn.mrdear.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import cn.mrdear.dao.UserDao;
import cn.mrdear.model.User;
import cn.mrdear.util.MockUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author Niu Li
 * @since 2017/3/30
 */
@Api(description = "提供增删改查,swagger示例",tags = "API-UserController")
@RestController
@RequestMapping(value = "/api/v1")
public class UserController {
  @Resource
  private UserDao userDao;

  @ApiOperation(value = "查询全部用户",httpMethod = "GET")
  @ApiResponse(code = 200,message = "Success")
  @RequestMapping(value = "/users",method = RequestMethod.GET)
  public List<User> getAllUsers(){
    return userDao.query();
  }

  @ApiOperation(value = "查询单个用户",notes = "根据传入id查找用户",httpMethod = "GET")
  @ApiResponse(code = 200,message = "Success")
  @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
  public User getUser(@PathVariable("id") Integer id){
    return userDao.find(id);
  }

  @ApiOperation(value = "批量插入用户",notes = "根据传入参数增加用户",httpMethod = "POST")
  @ApiResponse(code = 200,message = "Success")
  @RequestMapping(value = "/user",method = RequestMethod.POST)
  public List<User> InsertUser(Integer nums){
    if (Objects.isNull(nums)){
      nums = 10;
    }
    List<User> users = new ArrayList<>();
    for (Integer i = 0; i < nums; i++) {
      User user = MockUserUtil.mockSignle();
      userDao.save(user);
      users.add(user);
    }
    return users;
  }


}
