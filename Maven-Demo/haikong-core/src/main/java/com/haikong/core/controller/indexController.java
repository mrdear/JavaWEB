package com.haikong.core.controller;

import com.haikong.common.entity.User;
import com.haikong.common.mapper.UserMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Niu Li
 * @date 2016/9/6
 */
@Controller
public class indexController {

    @Resource
    private UserMapper userMapper;


    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public @ResponseBody User toIndex(){

        User user = userMapper.find("ov4a6wg2snvQ3eJiZcxJI31noVZk");
        return user;
    }
}
