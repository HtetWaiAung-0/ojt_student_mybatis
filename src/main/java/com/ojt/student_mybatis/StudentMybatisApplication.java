package com.ojt.student_mybatis;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ojt.student_mybatis.model.Course;
import com.ojt.student_mybatis.model.CourseStudent;
import com.ojt.student_mybatis.model.Student;
import com.ojt.student_mybatis.model.User;


@MappedTypes({User.class,Student.class,CourseStudent.class,Course.class})
@MapperScan("com.ojt.student_mybatis.mapper")
@SpringBootApplication
public class StudentMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentMybatisApplication.class, args);
	}

}
