package com.ehxy.tsg.service;

import com.ehxy.tsg.entity.Tag;
import com.ehxy.tsg.entity.BookTag;
import com.ehxy.tsg.mapper.TagMapper;
import com.ehxy.tsg.mapper.BookTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BookTagMapper bookTagMapper;

    public Tag selectById(Long id) {
        return tagMapper.selectById(id);
    }

    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    public int insert(Tag tag) {
        return tagMapper.insert(tag);
    }

    public int update(Tag tag) {
        return tagMapper.update(tag);
    }

    public int deleteById(Long id) {
        return tagMapper.deleteById(id);
    }

    public List<BookTag> getTagsByBookId(Long bookId) {
        return bookTagMapper.selectByBookId(bookId);
    }

    public int addTagToBook(Long bookId, Long tagId) {
        BookTag bookTag = new BookTag();
        bookTag.setBookId(bookId);
        bookTag.setTagId(tagId);
        return bookTagMapper.insert(bookTag);
    }

    public int removeTagFromBook(Long bookId, Long tagId) {
        return bookTagMapper.deleteByBookIdAndTagId(bookId, tagId);
    }
}
