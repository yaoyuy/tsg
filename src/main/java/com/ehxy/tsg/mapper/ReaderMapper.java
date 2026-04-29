package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Reader;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ReaderMapper {
    @Select("SELECT * FROM reader WHERE id = #{id}")
    Reader selectById(Long id);

    @Select("SELECT * FROM reader WHERE reader_no = #{readerNo}")
    Reader selectByReaderNo(String readerNo);

    @Select("SELECT * FROM reader")
    List<Reader> selectAll();

    @Select("<script>SELECT * FROM reader WHERE 1=1" +
            "<if test='name != null and name != \"\"'> AND name LIKE CONCAT('%',#{name},'%')</if>" +
            "<if test='department != null and department != \"\"'> AND department = #{department}</if>" +
            "<if test='status != null'> AND status = #{status}</if>" +
            "</script>")
    List<Reader> selectByCondition(Reader reader);

    @Insert("INSERT INTO reader (reader_no, name, password, gender, phone, email, department, max_borrow_count, status) VALUES (#{readerNo}, #{name}, #{password}, #{gender}, #{phone}, #{email}, #{department}, #{maxBorrowCount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Reader reader);

    @Update("UPDATE reader SET reader_no=#{readerNo}, name=#{name}, password=#{password}, gender=#{gender}, phone=#{phone}, email=#{email}, department=#{department}, max_borrow_count=#{maxBorrowCount}, status=#{status} WHERE id=#{id}")
    int update(Reader reader);

    @Delete("DELETE FROM reader WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM reader WHERE status = 1")
    int countActiveReaders();
}
