package com.ojt.student_mybatis.controller;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ojt.student_mybatis.mapper.CourseMapper;
import com.ojt.student_mybatis.mapper.CourseStudentMapper;
import com.ojt.student_mybatis.mapper.StudentMapper;
import com.ojt.student_mybatis.model.Course;
import com.ojt.student_mybatis.model.CourseStudent;
import com.ojt.student_mybatis.model.Student;


@Controller
public class StudentController {
	@Autowired
	private CourseMapper courseMapper;
	@Autowired
	private StudentMapper studentMapper;
    @Autowired
    private CourseStudentMapper courseStudentMapper;

	@RequestMapping(value = "/stuAddPage", method = RequestMethod.GET)
	public ModelAndView stuAddPage(ModelMap model) {
		Student stuBean = new Student();
		Student i = studentMapper.getId();
		String finalStuString = "STU" + String.format("%03d", i.getId());
		stuBean.setStuId(finalStuString);
		List<Course> s = courseMapper.findAllCourse();
		model.addAttribute("courseList", s);
		return new ModelAndView("STU001", "stuBean", stuBean);
	}

	@RequestMapping(value = "/stuAddNextPage", method = RequestMethod.GET)
	public ModelAndView stuAddNextPage(ModelMap model) {
		Student stuBean = new Student();
		Student i = studentMapper.getId();
		String finalStuString = "STU" + String.format("%03d", i.getId());
		stuBean.setStuId(finalStuString);
		List<Course> s = courseMapper.findAllCourse();
		model.addAttribute("courseList", s);
		model.addAttribute("errorFill", "Success Add");
		return new ModelAndView("STU001", "stuBean", stuBean);
	}

	@RequestMapping(value = "/addStu", method = RequestMethod.POST)
	public String addStu(@ModelAttribute("stuBean") Student stuBean, ModelMap model) {
        List<String> stuCourseId = stuBean.getStuCourseId();
		if (stuBean.getStuName().isBlank() || stuBean.getStuDob().isBlank() || stuBean.getStuGender().isBlank()
				|| stuBean.getStuPhone().isBlank() || stuBean.getStuEducation().isBlank()) {
			model.addAttribute("errorFill", "Fill the Blank!!!");
			model.addAttribute("courseList", courseMapper.findAllCourse());
			return "STU001";
		} else {
            
            CourseStudent courseStudent = new CourseStudent();
            for(String courseId : stuCourseId ) {
                courseStudent.setStuId(stuBean.getStuId());
                courseStudent.setCourseId(courseId);
                courseStudentMapper.saveCourseStudent(courseStudent);
            }
			studentMapper.saveStu(stuBean);
			return "redirect:/stuAddNextPage";

		}
	}

	@RequestMapping(value = "/updateStu", method = RequestMethod.POST)
	public String updateStu(@ModelAttribute("stuBean") Student stuBean, ModelMap model) {
        List<String> stuCourseId = stuBean.getStuCourseId();
		if (stuBean.getStuName().isBlank() || stuBean.getStuDob().isBlank() || stuBean.getStuGender().isBlank()
		|| stuBean.getStuPhone().isBlank() || stuBean.getStuEducation().isBlank()) {
			model.addAttribute("errorFill", "Fill the Blank!!!");
			model.addAttribute("courseList", courseMapper.findAllCourse());
			return "STU002";
		} else {
            courseStudentMapper.deleteCourseStudent(stuBean.getStuId());
            CourseStudent courseStudent = new CourseStudent();
            for(String courseId : stuCourseId ) {
                courseStudent.setStuId(stuBean.getStuId());
                courseStudent.setCourseId(courseId);
                courseStudentMapper.saveCourseStudent(courseStudent);
            }
			studentMapper.updateStu(stuBean);
			return "redirect:/stuSearchPage";
		}
	}

	@RequestMapping(value = "/updateStuPage", method = RequestMethod.GET)
	public ModelAndView updateStuPage(@RequestParam("id") int id,@RequestParam("stuId")String stuId, ModelMap model) {

		List<Student> res = studentMapper.findByStuId(stuId);
		
		model.addAttribute("courseList", courseMapper.findAllCourse());
		model.addAttribute("stu", res);
		return new ModelAndView("STU002", "stuBean",  studentMapper.findById(id));
	}

	@RequestMapping(value = "/deleteStu", method = RequestMethod.GET)
	public String deleteStu(@RequestParam("id") int id, ModelMap model) {

		studentMapper.deleteStu(id);
		return "redirect:/stuSearchPage";
	}

	@RequestMapping(value = "/stuSearchPage", method = RequestMethod.GET)
	public String stuSearchPage(ModelMap model) {

		List<Student> list = studentMapper.findAllStu();
        for(Student student : list) {
            List<String> clist = courseStudentMapper.findByStuId(student.getStuId());
            student.setStuCourseId(clist);   
        }
		model.addAttribute("stuList", list);
		return "STU003";
	}

	@RequestMapping(value = "/searchStu", method = RequestMethod.POST)
	public String searchStu(@ModelAttribute("stuBean") Student stuBean, @RequestParam("searchId") String searchId,
			@RequestParam("searchName") String searchName,
			@RequestParam("searchCourse") String searchCourse, ModelMap model) {

		List<Student> showList = new ArrayList<>();
		if (searchId.isBlank() && searchName.isBlank() && searchCourse.isBlank()) {

			showList = studentMapper.findAllStu();
            for(Student student : showList) {
                List<String> clist = courseStudentMapper.findByStuId(student.getStuId());
                student.setStuCourseId(clist);   
            }
			model.addAttribute("stuList", showList);
			return "STU003";
		} else {
			searchId = searchId.isBlank() ? "#$*@" : searchId;
			searchName = searchName.isBlank() ? "#$*@" : "%"+searchName+"%";
			searchCourse = searchCourse.isBlank() ? "#$*@" : "%"+searchCourse+"%";
			showList = studentMapper.searchStu(searchId,
					searchName, searchCourse);
                    for(Student student : showList) {
                        List<String> clist = courseStudentMapper.findByStuId(student.getStuId());
                        student.setStuCourseId(clist);   
                    }

			model.addAttribute("stuList", showList);
			return "STU003";
		}
	}
}