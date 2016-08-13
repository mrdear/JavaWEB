package cn.mrdear.controller;

import cn.mrdear.entity.User;
import cn.mrdear.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Niu Li
 * @date 2016/8/9
 */
@Controller
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Resource
    private UserMapper userMapper;
    /**
     * 测试hello
     * @return
     */
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(Model model) {
        model.addAttribute("name", "Dear");
        return "hello";
    }

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public @ResponseBody User testUserDao(){
        User user = userMapper.findById(1);
        return user;
    }

}
