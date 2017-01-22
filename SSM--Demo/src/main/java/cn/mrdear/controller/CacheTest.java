package cn.mrdear.controller;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import cn.mrdear.entity.Book;
import cn.mrdear.mapper.BookMapper;
import cn.mrdear.service.CacheTestService;

/**
 * @author Niu Li
 * @date 2016/9/22
 */
@RestController
public class CacheTest {
    @Resource
    private BookMapper bookMapper;

    @RequestMapping(value = "/books")
    @ResponseBody
    public List<Book> test(){
        List<Book> books = bookMapper.selectAll();
        return books;
    }
}
