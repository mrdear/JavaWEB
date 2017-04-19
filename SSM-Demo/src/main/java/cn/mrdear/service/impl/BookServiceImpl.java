package cn.mrdear.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import cn.mrdear.entity.Book;
import cn.mrdear.mapper.BookMapper;
import cn.mrdear.service.BookService;

/**
 * @author Niu Li
 * @since  2017/1/23
 */
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    @Cacheable(cacheNames = "bookcache")
    public List<Book> findAll() {
        List<Book> books = bookMapper.selectAll();
        System.out.println("测试缓存,如果缓存开启,多次请求则该方法会执行一次");
        return books;
    }
}
