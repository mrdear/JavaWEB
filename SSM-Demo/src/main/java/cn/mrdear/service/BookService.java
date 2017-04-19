package cn.mrdear.service;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;

import cn.mrdear.entity.Book;

/**
 * @author Niu Li
 * @date 2016/9/23
 */
public interface BookService {
    /**
     * 查询所有的books
     * @return 结果集
     */
    List<Book> findAll();
}
