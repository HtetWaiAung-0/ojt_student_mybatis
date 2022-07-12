package com.ojt.student_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.ojt.student_mybatis.model.User;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAllUser();

    @Select("select * from user where id = #{id}")
    User findByUserId(int id);

    @Update("update user set userId=#{userId},userMail=#{userMail},userPassword=#{userPassword},userRole=#{userRole} where id=#{id}")
    void updateUser(User user);

    @Insert("insert into user(userId,userMail,userPassword,userRole) values(#{userId},#{userMail},#{UserPassword},#{userRole})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveUser(User user);

    @Delete("delete from user where id = #{id}")
    void deleteUser(int id);

    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE userMail={userMail},userPassword={userPassword})")
    boolean existsByEmailAndPassword(String userMail,String userPassword);

    @Select("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'yoyo' AND TABLE_NAME = 'course';")
    @Results(value = { @Result(property = "id", column = "AUTO_INCREMENT")})
    int getId();

    @Select("select * from user where userMail=? AND userpassword=?")
    List<User> findByUserIdOrUserMail(String userId,String userMail);
}
