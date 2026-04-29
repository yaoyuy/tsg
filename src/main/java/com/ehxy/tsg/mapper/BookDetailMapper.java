package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.BookDetail;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookDetailMapper {
    @Select("SELECT * FROM book_detail WHERE id = #{id}")
    BookDetail selectById(Long id);

    @Select("SELECT * FROM book_detail WHERE book_id = #{bookId}")
    BookDetail selectByBookId(Long bookId);

    @Select("SELECT * FROM book_detail")
    List<BookDetail> selectAll();

    @Insert("INSERT INTO book_detail (book_id, page_count, language, edition, format, weight, dimensions, summary) VALUES (#{bookId}, #{pageCount}, #{language}, #{edition}, #{format}, #{weight}, #{dimensions}, #{summary})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BookDetail detail);

    @Update("UPDATE book_detail SET page_count=#{pageCount}, language=#{language}, edition=#{edition}, format=#{format}, weight=#{weight}, dimensions=#{dimensions}, summary=#{summary} WHERE book_id=#{bookId}")
    int updateByBookId(BookDetail detail);

    @Delete("DELETE FROM book_detail WHERE id = #{id}")
    int deleteById(Long id);
}
