package com.se52.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se52.entity.Authority;
import com.se52.entity.User;
import com.se52.repository.AuthorityDAO;
import com.se52.repository.UserDAO;
import com.se52.util.WebUtil;

@Controller
public class AdminController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AuthorityDAO authDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/admin", method=RequestMethod.GET)
	public @ResponseBody ModelAndView home (){
		return new ModelAndView("redirect:/admin/revenue");
	}
	
	@RequestMapping(value = "/admin/revenue", method=RequestMethod.GET)
	public @ResponseBody ModelAndView revenue (){
		List<User> users = userDAO.findAllUser();
		ModelAndView model = new ModelAndView("admin/revenue");
		model.addObject("users", WebUtil.convertUsersToJSON(users));
		
		return model;
	}
	
	@RequestMapping(value = "/admin/user-management", method=RequestMethod.GET)
	public @ResponseBody ModelAndView userManagement (){
		List<User> users = userDAO.findAllUser();
		ModelAndView model = new ModelAndView("admin/userManagement");
		model.addObject("users", WebUtil.convertUsersToJSON(users));
		
		return model;
	}
	
	@RequestMapping(value = "/admin/user/update", method=RequestMethod.POST)
	public @ResponseBody String updateUser (@RequestBody User user){
		if(user.getId() == 0){
			Date date = new Date();
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setLastPasswordResetDate(date);
		}
		List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(authDAO.findOne(2));
        user.setAuthorities(authorities);
		userDAO.save(user);
		return "success";
	}
	
	@RequestMapping(value = "/admin/user/{id}", method=RequestMethod.DELETE)
	public @ResponseBody String deleteStaff(@PathVariable int id){
		userDAO.delete(id);
		return "success";
	}

}
