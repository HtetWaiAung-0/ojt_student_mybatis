package com.ojt.student_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ojt.student_mybatis.model.CourseStudent;

@Mapper
public interface CourseStudentMapper {
    @Insert("insert into course_student (stuId,courseId) values(#{stuId},#{courseId})")
    void saveCourseStudent(CourseStudent courseStudent); 

    @Select("select * from course_student")
    List<CourseStudent> findAllCourseStudent();

    @Delete("delete from course_student where stuId=#{stuId}")
    void deleteCourseStudent(String stuId);

    @Select("select courseId form course_student where stuId=#{stuId}")
    List<String> findByStuId(String stuId);
}
