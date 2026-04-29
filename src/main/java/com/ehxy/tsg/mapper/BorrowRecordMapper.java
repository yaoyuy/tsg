package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.BorrowRecord;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BorrowRecordMapper {
    @Select("SELECT * FROM borrow_record WHERE id = #{id}")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    BorrowRecord selectById(Long id);

    @Select("SELECT * FROM borrow_record WHERE reader_id = #{readerId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    List<BorrowRecord> selectByReaderId(Long readerId);

    @Select("SELECT * FROM borrow_record WHERE status = 0 ORDER BY due_date ASC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    List<BorrowRecord> selectActiveBorrows();

    @Select("<script>SELECT * FROM borrow_record WHERE 1=1" +
            "<if test='readerId != null'> AND reader_id = #{readerId}</if>" +
            "<if test='bookId != null'> AND book_id = #{bookId}</if>" +
            "<if test='status != null'> AND status = #{status}</if>" +
            " ORDER BY create_time DESC</script>")
    List<BorrowRecord> selectByCondition(BorrowRecord record);

    @Select("SELECT * FROM borrow_record ORDER BY create_time DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    List<BorrowRecord> selectAll();

    @Insert("INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, status) VALUES (#{readerId}, #{bookId}, #{borrowDate}, #{dueDate}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BorrowRecord record);

    @Update("UPDATE borrow_record SET return_date=#{returnDate}, fine=#{fine}, status=#{status}, remark=#{remark}, due_date=#{dueDate} WHERE id=#{id}")
    int update(BorrowRecord record);

    @Delete("DELETE FROM borrow_record WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM borrow_record WHERE reader_id = #{readerId} AND status = 0")
    int countActiveBorrowsByReader(Long readerId);

    @Select("SELECT COUNT(*) FROM borrow_record")
    int countAll();

    @Select("SELECT * FROM borrow_record WHERE status = 0 AND due_date < CURDATE() ORDER BY due_date ASC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "book", column = "book_id", one = @One(select = "com.ehxy.tsg.mapper.BookMapper.selectById"))
    })
    List<BorrowRecord> selectOverdue();

    @Select("SELECT COUNT(*) FROM borrow_record WHERE status = 0 AND due_date < CURDATE()")
    int countOverdue();
}
