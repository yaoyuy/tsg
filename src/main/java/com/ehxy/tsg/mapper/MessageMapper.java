package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Message;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM message WHERE id = #{id}")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    Message selectById(Long id);

    @Select("SELECT * FROM message WHERE reader_id = #{readerId} ORDER BY create_time DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    List<Message> selectByReaderId(Long readerId);

    @Select("SELECT * FROM message WHERE reader_id = #{readerId} AND is_read = 0 ORDER BY create_time DESC")
    @Results({
        @Result(property = "reader", column = "reader_id", one = @One(select = "com.ehxy.tsg.mapper.ReaderMapper.selectById"))
    })
    List<Message> selectUnreadByReaderId(Long readerId);

    @Insert("INSERT INTO message (reader_id, title, content, is_read) VALUES (#{readerId}, #{title}, #{content}, #{isRead})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Message message);

    @Update("UPDATE message SET is_read = 1 WHERE id = #{id}")
    int markAsRead(Long id);

    @Update("UPDATE message SET is_read = 1 WHERE reader_id = #{readerId}")
    int markAllAsRead(Long readerId);

    @Delete("DELETE FROM message WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM message WHERE reader_id = #{readerId} AND is_read = 0")
    int countUnreadByReaderId(Long readerId);
}
