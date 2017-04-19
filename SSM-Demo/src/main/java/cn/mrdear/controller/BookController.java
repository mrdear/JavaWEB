package cn.mrdear.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

import cn.mrdear.entity.Book;
import cn.mrdear.service.BookService;

/**
 * @author Niu Li
 * @since  2016/9/22
 */
@RestController
public class BookController {

    @Resource
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> books(){
        return bookService.findAll();
    }
}
