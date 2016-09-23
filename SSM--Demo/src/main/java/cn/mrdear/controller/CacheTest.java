package cn.mrdear.controller;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import cn.mrdear.service.CacheTestService;

/**
 * @author Niu Li
 * @date 2016/9/22
 */
@RestController
public class CacheTest {
    @Resource
    private CacheTestService CacheTestService;

    @RequestMapping(value = "/test")
    public JSONObject test(int id){
        JSONObject object = CacheTestService.getjson(id);
        JSONObject object1 = CacheTestService.getjson(id);
        System.out.println(object == object1);
        return CacheTestService.getjson(id);
    }
}
