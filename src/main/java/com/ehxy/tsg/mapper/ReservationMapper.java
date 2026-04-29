package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Reservation;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ReservationMapper {
    @Select("SELECT * FROM reservation WHERE id = #{id}")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    Reservation selectById(Long id);

    @Select("SELECT * FROM reservation WHERE reader_id = #{readerId} ORDER BY reserve_date DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    List<Reservation> selectByReaderId(Long readerId);

    @Select("SELECT * FROM reservation WHERE status = 0 ORDER BY reserve_date ASC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    List<Reservation> selectActiveReservations();

    @Select("SELECT * FROM reservation")
    List<Reservation> selectAll();

    @Insert("INSERT INTO reservation (reader_id, book_id, expire_date, status) VALUES (#{readerId}, #{bookId}, #{expireDate}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Reservation reservation);

    @Update("UPDATE reservation SET status=#{status} WHERE id=#{id}")
    int update(Reservation reservation);

    @Delete("DELETE FROM reservation WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM reservation WHERE reader_id = #{readerId} AND book_id = #{bookId} AND status = 0")
    int countActiveByReaderAndBook(Long readerId, Long bookId);
}
