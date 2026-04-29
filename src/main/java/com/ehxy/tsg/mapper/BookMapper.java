package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Book;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookMapper {
    @Select("SELECT b.*, bc.category_name FROM book b LEFT JOIN book_category bc ON b.category_id = bc.id WHERE b.id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "category", column = "category_id", one = @One(select = "com.ehxy.tsg.mapper.BookCategoryMapper.selectById"))
    })
    Book selectById(Long id);

    @Select("<script>SELECT b.* FROM book b WHERE 1=1" +
            "<if test='title != null'> AND b.title LIKE CONCAT('%',#{title},'%')</if>" +
            "<if test='author != null'> AND b.author LIKE CONCAT('%',#{author},'%')</if>" +
            "<if test='categoryId != null'> AND b.category_id = #{categoryId}</if>" +
            "<if test='isbn != null'> AND b.isbn = #{isbn}</if>" +
            "</script>")
    List<Book> selectByCondition(Book book);

    @Select("SELECT b.* FROM book b ORDER BY b.create_time DESC LIMIT #{limit}")
    List<Book> selectLatestBooks(int limit);

    @Select("SELECT * FROM book")
    List<Book> selectAll();

    @Insert("INSERT INTO book (isbn, title, author, publisher, publish_date, price, category_id, total_count, available_count, location, description, keywords) VALUES (#{isbn}, #{title}, #{author}, #{publisher}, #{publishDate}, #{price}, #{categoryId}, #{totalCount}, #{availableCount}, #{location}, #{description}, #{keywords})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Book book);

    @Update("UPDATE book SET isbn=#{isbn}, title=#{title}, author=#{author}, publisher=#{publisher}, publish_date=#{publishDate}, price=#{price}, category_id=#{categoryId}, total_count=#{totalCount}, available_count=#{availableCount}, location=#{location}, description=#{description}, keywords=#{keywords} WHERE id=#{id}")
    int update(Book book);

    @Update("UPDATE book SET available_count = available_count - 1 WHERE id = #{id} AND available_count > 0")
    int decreaseAvailableCount(Long id);

    @Update("UPDATE book SET available_count = available_count + 1 WHERE id = #{id}")
    int increaseAvailableCount(Long id);

    @Delete("DELETE FROM book WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM book")
    int countAll();

    @Select("SELECT COUNT(*) FROM book WHERE available_count > 0")
    int countAvailable();

    @Select("SELECT b.*, COUNT(br.id) as borrow_count FROM book b " +
            "LEFT JOIN borrow_record br ON b.id = br.book_id " +
            "GROUP BY b.id ORDER BY borrow_count DESC LIMIT #{limit}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "category", column = "category_id", one = @One(select = "com.ehxy.tsg.mapper.BookCategoryMapper.selectById"))
    })
    List<Book> selectPopularBooks(int limit);
}
