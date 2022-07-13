package com.ojt.student_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ojt.student_mybatis.model.Student;

@Mapper
public interface StudentMapper {
    @Select("select * from student")
    List<Student> findAllStu();

    @Update("update student set stuId=#{stuId},stuName=#{stuName},stuDob=#{stuDob},stuGender=#{stuGender},stuPhone=#{stuPhone},stuEducation=#{stuEducation} where id=#{id}")
    void updateStu(Student student);

    @Delete("delete from student where id=#{id}")
    void deleteStu(int id);

    @Select("select * from student where id=#{id}")
    List<Student> findById(int id);

    @Select("select * from student where stuId=#{stuId}")
    List<Student> findByStuId(String stuId);

    @Insert("insert into student(stuId,stuName,stuDob,stuGender,stuPhone,stuEducation) values(#{stuId},#{stuName},#{stuDob},#{stuGender},#{stuPhone},#{stuEducation})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveStu(Student student);

    @Select("SELECT distinct student.stuId,student.stuName FROM mybatis.student right JOIN mybatis.course_student ON student.stuId=course_student.stuId where student.stuId=#{stuId} OR student.stuName Like #{stuName} OR course_student.courseName Like #{courseName}")
    List<Student> searchStu(String stuId,String stuName,String courseName);

    @Select("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mybatis' AND TABLE_NAME = 'student'")
    @Results(value = { @Result(property = "id", column = "AUTO_INCREMENT")})
    Student getId();

}
