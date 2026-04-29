package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Favorite;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("SELECT * FROM favorite WHERE id = #{id}")
    @Results({
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById")),
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    Favorite selectById(Long id);

    @Select("SELECT * FROM favorite WHERE reader_id = #{readerId}")
    @Results({
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById")),
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    List<Favorite> selectByReaderId(Long readerId);

    @Select("SELECT * FROM favorite WHERE reader_id = #{readerId} AND book_id = #{bookId}")
    Favorite selectByReaderIdAndBookId(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

    @Insert("INSERT INTO favorite (reader_id, book_id) VALUES (#{readerId}, #{bookId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Favorite favorite);

    @Delete("DELETE FROM favorite WHERE id = #{id}")
    int deleteById(Long id);

    @Delete("DELETE FROM favorite WHERE reader_id = #{readerId} AND book_id = #{bookId}")
    int deleteByReaderIdAndBookId(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

    @Select("SELECT COUNT(*) FROM favorite WHERE book_id = #{bookId}")
    int countByBookId(Long bookId);

    @Select("SELECT COUNT(*) FROM favorite WHERE reader_id = #{readerId}")
    int countByReaderId(Long readerId);
}
