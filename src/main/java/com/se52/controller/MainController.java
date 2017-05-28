package com.se52.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se52.entity.Authority;
import com.se52.entity.User;
import com.se52.repository.AuthorityDAO;
import com.se52.repository.UserDAO;

@Controller
public class MainController implements ErrorController{
	
	private static final String PATH = "/error";
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AuthorityDAO authDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthorityDAO authorityDAO;
	
	@GetMapping(value = "/")
	public @ResponseBody String home (){
		return "nothing";
	}
	
	@GetMapping(value = "/index")
	public @ResponseBody ModelAndView index (){
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = PATH, method=RequestMethod.GET)
    public ModelAndView error() {
        return new ModelAndView("redirect:/index");
    }
	
	@GetMapping(value = "/403")
	public @ResponseBody String forbidden (){
		return "403 Forbidden!";
	}
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return PATH;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody String doRegister(@RequestBody User user){
		
		
		Date date = new Date();
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setLastPasswordResetDate(date);
		List<Authority> authorities = new ArrayList<Authority>();
        authorities.add(authDAO.findOne(2));
        user.setAuthorities(authorities);
		
        userDAO.save(user);
		return "success";
	}
}
