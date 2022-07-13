package com.ojt.student_mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;


import com.ojt.student_mybatis.model.Course;

@Mapper
public interface CourseMapper {
    
    @Insert("insert into course(courseId,courseName) values(#{courseId},#{courseName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void saveCourse(Course course);

    @Select("select * from course")
    List<Course> findAllCourse();

    @Select("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mybatis' AND TABLE_NAME = 'course'")
    @Results(value = { @Result(property = "id", column = "AUTO_INCREMENT")})
    Course getId();
    

}
