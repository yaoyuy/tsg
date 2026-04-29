package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.BookTag;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookTagMapper {
    @Select("SELECT * FROM book_tag WHERE id = #{id}")
    BookTag selectById(Long id);

    @Select("SELECT bt.*, t.tag_name FROM book_tag bt LEFT JOIN tag t ON bt.tag_id = t.id WHERE bt.book_id = #{bookId}")
    List<BookTag> selectByBookId(Long bookId);

    @Select("SELECT bt.*, t.tag_name FROM book_tag bt LEFT JOIN tag t ON bt.tag_id = t.id WHERE bt.tag_id = #{tagId}")
    List<BookTag> selectByTagId(Long tagId);

    @Select("SELECT * FROM book_tag")
    List<BookTag> selectAll();

    @Insert("INSERT INTO book_tag (book_id, tag_id) VALUES (#{bookId}, #{tagId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BookTag bookTag);

    @Delete("DELETE FROM book_tag WHERE book_id = #{bookId} AND tag_id = #{tagId}")
    int deleteByBookIdAndTagId(Long bookId, Long tagId);

    @Delete("DELETE FROM book_tag WHERE book_id = #{bookId}")
    int deleteByBookId(Long bookId);
}
