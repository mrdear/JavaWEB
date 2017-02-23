package cn.mrdear.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import cn.mrdear.entity.Book;
import cn.mrdear.service.BookService;

/**
 * @author Niu Li
 * @date 2016/9/22
 */
@RestController
public class BookController {
    @Resource
    private BookService bookService;

    @RequestMapping(value = "/books")
    @ResponseBody
    public List<Book> books(){
        List<Book> books = bookService.findAll();
        return books;
    }
}
