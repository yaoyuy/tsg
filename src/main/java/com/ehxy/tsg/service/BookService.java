package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Book;
import com.ehxy.tsg.entity.BookCategory;
import com.ehxy.tsg.entity.BookDetail;
import com.ehxy.tsg.entity.BookTag;
import com.ehxy.tsg.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    @Autowired
    private BookDetailMapper bookDetailMapper;
    @Autowired
    private BookTagMapper bookTagMapper;

    public Book selectById(Long id) {
        Book book = bookMapper.selectById(id);
        if (book != null) {
            book.setDetail(bookDetailMapper.selectByBookId(id));
            book.setCategory(bookCategoryMapper.selectById(book.getCategoryId()));
        }
        return book;
    }

    public List<Book> selectAll() {
        return bookMapper.selectAll();
    }

    public List<Book> selectByCondition(Book book) {
        return bookMapper.selectByCondition(book);
    }

    public List<Book> selectLatestBooks(int limit) {
        return bookMapper.selectLatestBooks(limit);
    }

    @Transactional
    public int insert(Book book) {
        bookMapper.insert(book);
        if (book.getDetail() != null) {
            book.getDetail().setBookId(book.getId());
            bookDetailMapper.insert(book.getDetail());
        }
        return 1;
    }

    @Transactional
    public int update(Book book) {
        bookMapper.update(book);
        if (book.getDetail() != null) {
            bookDetailMapper.updateByBookId(book.getDetail());
        }
        return 1;
    }

    @Transactional
    public int deleteById(Long id) {
        bookTagMapper.deleteByBookId(id);
        bookDetailMapper.deleteById(id);
        return bookMapper.deleteById(id);
    }

    public List<BookCategory> getAllCategories() {
        return bookCategoryMapper.selectAll();
    }

    public int countAll() {
        return bookMapper.countAll();
    }

    public int countAvailable() {
        return bookMapper.countAvailable();
    }

    public List<Book> selectPopularBooks(int limit) {
        return bookMapper.selectPopularBooks(limit);
    }
}
