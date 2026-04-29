package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.FineRecord;
import org.apache.ibatis.annotations.*;
import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface FineRecordMapper {
    @Select("SELECT * FROM fine_record WHERE id = #{id}")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "borrowRecord", column = "borrow_id", one = @One(select = "com.ehxy.tsg.mapper.BorrowRecordMapper.selectById"))
    })
    FineRecord selectById(Long id);

    @Select("SELECT * FROM fine_record WHERE reader_id = #{readerId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "borrowRecord", column = "borrow_id", one = @One(select = "com.ehxy.tsg.mapper.BorrowRecordMapper.selectById"))
    })
    List<FineRecord> selectByReaderId(Long readerId);

    @Select("SELECT * FROM fine_record WHERE is_paid = 0 ORDER BY create_time DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById")),
        @Result(property = "borrowRecord", column = "borrow_id", one = @One(select = "com.ehxy.tsg.mapper.BorrowRecordMapper.selectById"))
    })
    List<FineRecord> selectUnpaid();

    @Insert("INSERT INTO fine_record (reader_id, borrow_id, amount, is_paid) VALUES (#{readerId}, #{borrowId}, #{amount}, #{isPaid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(FineRecord fineRecord);

    @Update("UPDATE fine_record SET is_paid = 1 WHERE id = #{id}")
    int markAsPaid(Long id);

    @Select("SELECT SUM(amount) FROM fine_record WHERE reader_id = #{readerId} AND is_paid = 0")
    BigDecimal sumUnpaidByReaderId(Long readerId);

    @Select("SELECT COUNT(*) FROM fine_record WHERE is_paid = 0")
    int countUnpaid();
}
