package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Review;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("SELECT * FROM review WHERE id = #{id}")
    @Results({
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById")),
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    Review selectById(Long id);

    @Select("SELECT * FROM review WHERE book_id = #{bookId} AND status = 1 ORDER BY create_time DESC")
    @Results({
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById")),
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    List<Review> selectByBookId(Long bookId);

    @Select("SELECT * FROM review WHERE reader_id = #{readerId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById")),
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    List<Review> selectByReaderId(Long readerId);

    @Select("SELECT * FROM review WHERE reader_id = #{readerId} AND book_id = #{bookId}")
    Review selectByReaderIdAndBookId(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

    @Select("SELECT * FROM review ORDER BY create_time DESC")
    @Results({
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById")),
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    List<Review> selectAll();

    @Insert("INSERT INTO review (reader_id, book_id, rating, content, status) VALUES (#{readerId}, #{bookId}, #{rating}, #{content}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Review review);

    @Update("UPDATE review SET rating=#{rating}, content=#{content} WHERE id=#{id}")
    int update(Review review);

    @Update("UPDATE review SET status=0 WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT AVG(rating) FROM review WHERE book_id = #{bookId} AND status = 1")
    Double getAverageRatingByBookId(Long bookId);

    @Select("SELECT COUNT(*) FROM review WHERE book_id = #{bookId} AND status = 1")
    int countByBookId(Long bookId);
}
