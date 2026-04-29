package com.ehxy.tsg.mapper;

import com.ehxy.tsg.entity.Admin;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE id = #{id}")
    Admin selectById(Long id);

    @Select("SELECT * FROM admin WHERE username = #{username}")
    Admin selectByUsername(String username);

    @Select("SELECT * FROM admin")
    List<Admin> selectAll();

    @Insert("INSERT INTO admin (username, password, real_name, phone, email, status) VALUES (#{username}, #{password}, #{realName}, #{phone}, #{email}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Admin admin);

    @Update("UPDATE admin SET username=#{username}, password=#{password}, real_name=#{realName}, phone=#{phone}, email=#{email}, status=#{status} WHERE id=#{id}")
    int update(Admin admin);

    @Delete("DELETE FROM admin WHERE id = #{id}")
    int deleteById(Long id);
}
