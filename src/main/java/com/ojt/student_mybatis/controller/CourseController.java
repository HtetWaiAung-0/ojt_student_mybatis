package com.ojt.student_mybatis.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ojt.student_mybatis.mapper.CourseMapper;
import com.ojt.student_mybatis.model.Course;


@Controller
public class CourseController {
    @Autowired
    private CourseMapper courseMapper;

    @RequestMapping(value="/courseAddPage", method=RequestMethod.GET)
    public ModelAndView courseAddPage() {
        Course cBean = new Course();
        Course i = courseMapper.getId();
        String format = "COU" + String.format("%03d", i.getId());
        cBean.setCourseId(format);
        return new ModelAndView("BUD003", "cBean", cBean);
    }

    @RequestMapping(value="/courseAddPageNext", method=RequestMethod.GET)
    public ModelAndView courseAddPageNext(ModelMap model) {
        Course cBean = new Course();
        Course i = courseMapper.getId();
        String format = "COU" + String.format("%03d", i.getId());
        cBean.setCourseId(format);
        model.addAttribute("errorFill", "Success Add");
        return new ModelAndView("BUD003", "cBean", cBean);
    }
    
    @RequestMapping(value = "/courseAdd", method = RequestMethod.POST)
    public String courseAdd(@ModelAttribute("cBean") Course cBean, ModelMap model) {
        if (cBean.getCourseName().isBlank()) {

            model.addAttribute("errorFill", "Fill the Blank!!!");
            return "BUD003";
        } else { 
            courseMapper.saveCourse(cBean);
            return "redirect:/courseAddPageNext";
        }

    }
}