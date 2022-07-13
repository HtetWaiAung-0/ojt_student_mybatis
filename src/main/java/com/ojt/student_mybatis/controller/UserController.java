package com.ojt.student_mybatis.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ojt.student_mybatis.mapper.UserMapper;
import com.ojt.student_mybatis.model.User;


@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;

    @PostMapping(value = "/login")
    public String login(@RequestParam("loginMail") String loginMail,@RequestParam("loginPassword") String loginPassword,HttpSession session,ModelMap model){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
		session.setAttribute("date", LocalDate.now().format(formatter));

        if(userMapper.existsByEmailAndPassword(loginMail, loginPassword)|| (loginMail.equals("admin@gmail.com") && loginPassword.equals("123"))){
            session.setAttribute("loginMail", loginMail);
            session.setAttribute("loginPassword", loginPassword);
            return "MNU001";
        }
        else{
            model.addAttribute("error","Please check userMail or userPassword");
            return "LGN001";
        }
    }

    @GetMapping(value="/logOut")
    public String logout(HttpSession session) {
        session.removeAttribute("loginMail");
        session.removeAttribute("loginPassword");
        session.removeAttribute("date");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping(value="/")
    public String loginPage() {
        return "LGN001";
    }
    
    @GetMapping(value="/addUserPage")
    public ModelAndView  addUserPage() {
        return new ModelAndView("USR001","userBean",new User());
    }
    
    @GetMapping(value="/addUserNextPage")
    public ModelAndView addUserNextPage(ModelMap model) {
        model.addAttribute("errorFill","Sucess Register");
        return new ModelAndView("USR001","userBean",new User());
    }

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("userBean") User userBean, @RequestParam("conPassword") String conPassword,
			ModelMap model) {

		if (userBean.getUserMail().isBlank() || userBean.getUserPassword().isBlank()
				|| conPassword.isBlank() || userBean.getUserRole().isBlank()) {
			model.addAttribute("errorFill", "Fill the Blank!!!");
			return "USR001";
		} else {
			User i = userMapper.getId();
			String stuIdString = String.format("%03d", i.getId());
			String finalString = "USR" + stuIdString;
			userBean.setUserId(finalString);
			userMapper.saveUser(userBean);
			return "redirect:/addUserNextPage";
		}
	}

    @GetMapping(value = "/updateUserPage")
	public ModelAndView updateUserPage(@RequestParam("id") int id, ModelMap model) {

		return new ModelAndView("USR002", "userBean", userMapper.findByUserId(id));
	}

	@GetMapping(value = "/searchUserPage")
	public String searchUserPage(ModelMap model) {
		List<User> list = userMapper.findAllUser();
		model.addAttribute("userList", list);
		return "USR003";
	}

	@PostMapping(value = "/searchUser")
	public String searchUser(@ModelAttribute("userBean") User userBean, @RequestParam("searchId") String searchId,
			@RequestParam("searchMail") String searchMail, ModelMap model) {
		List<User> showList = new ArrayList<>();
		if (searchId.isBlank() && searchMail.isBlank()) {
			showList = userMapper.findAllUser();
			model.addAttribute("userList", showList);
			return "USR003";
		} else {
			searchId = searchId.isBlank() ? "#$*@" : searchId ;
			searchMail = searchMail.isBlank() ? "#$*@" : "%"+searchMail+"%";
			showList = userMapper.findByUserIdOrUserMail(searchId, searchMail);
			model.addAttribute("userList", showList);
			return "USR003";
		}
	}

	@PostMapping(value = "/updateUser")
	public String updateUser(@ModelAttribute("userBean") User userBean, @RequestParam("conPassword") String conPassword,
			ModelMap model) {
		if (userBean.getUserMail().isBlank() || userBean.getUserPassword().isBlank()
				|| conPassword.isBlank() || userBean.getUserRole().isBlank()) {
			model.addAttribute("errorFill", "Fill the Blank!!!");
			return "USR002";
		} else {
			userMapper.updateUser(userBean);
			model.addAttribute("errorFill", "Success Register!");
			return "redirect:/searchUserPage";
		}
	}

	@GetMapping(value = "/deleteUser")
	public String deleteUser(@RequestParam("id") int id, ModelMap model) {
		
		userMapper.deleteUser(id);
		return "redirect:/searchUserPage";
	}
    
    
}
