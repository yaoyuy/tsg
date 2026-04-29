package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Announcement;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AnnouncementMapper {
    @Select("SELECT * FROM announcement WHERE id = #{id}")
    @Results({
        @Result(property = "publisher", column = "publisher_id", one = @One(select = "com.ehxy.tsg.mapper.AdminMapper.selectById"))
    })
    Announcement selectById(Long id);

    @Select("SELECT * FROM announcement WHERE status = 1 ORDER BY publish_date DESC")
    @Results({
        @Result(property = "publisher", column = "publisher_id", one = @One(select = "com.ehxy.tsg.mapper.AdminMapper.selectById"))
    })
    List<Announcement> selectActiveAnnouncements();

    @Select("SELECT * FROM announcement ORDER BY publish_date DESC")
    @Results({
        @Result(property = "publisher", column = "publisher_id", one = @One(select = "com.ehxy.tsg.mapper.AdminMapper.selectById"))
    })
    List<Announcement> selectAll();

    @Insert("INSERT INTO announcement (title, content, publisher_id, status) VALUES (#{title}, #{content}, #{publisherId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Announcement announcement);

    @Update("UPDATE announcement SET title=#{title}, content=#{content}, status=#{status} WHERE id=#{id}")
    int update(Announcement announcement);

    @Delete("DELETE FROM announcement WHERE id = #{id}")
    int deleteById(Long id);
}
