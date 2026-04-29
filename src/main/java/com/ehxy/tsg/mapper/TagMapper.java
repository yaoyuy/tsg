package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Tag;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TagMapper {
    @Select("SELECT * FROM tag WHERE id = #{id}")
    Tag selectById(Long id);

    @Select("SELECT * FROM tag")
    List<Tag> selectAll();

    @Insert("INSERT INTO tag (tag_name, description) VALUES (#{tagName}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Tag tag);

    @Update("UPDATE tag SET tag_name=#{tagName}, description=#{description} WHERE id=#{id}")
    int update(Tag tag);

    @Delete("DELETE FROM tag WHERE id = #{id}")
    int deleteById(Long id);
}
