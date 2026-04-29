package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.BookCategory;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookCategoryMapper {
    @Select("SELECT * FROM book_category WHERE id = #{id}")
    BookCategory selectById(Long id);

    @Select("SELECT * FROM book_category WHERE parent_id = #{parentId} ORDER BY sort_order")
    List<BookCategory> selectByParentId(Long parentId);

    @Select("SELECT * FROM book_category ORDER BY sort_order")
    List<BookCategory> selectAll();

    @Insert("INSERT INTO book_category (category_name, parent_id, sort_order, description) VALUES (#{categoryName}, #{parentId}, #{sortOrder}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BookCategory category);

    @Update("UPDATE book_category SET category_name=#{categoryName}, parent_id=#{parentId}, sort_order=#{sortOrder}, description=#{description} WHERE id=#{id}")
    int update(BookCategory category);

    @Delete("DELETE FROM book_category WHERE id = #{id}")
    int deleteById(Long id);
}
