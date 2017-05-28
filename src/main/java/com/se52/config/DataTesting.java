/*package com.se52.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.se52.entity.Authority;
import com.se52.entity.StaffRelationship;
import com.se52.entity.User;
import com.se52.repository.AuthorityDAO;
import com.se52.repository.UserDAO;

@Component
public class DataTesting implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AuthorityDAO authDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		// Admin account
	    if (userDAO.findByUsername("admin") == null) {
	    	Date date = new Date();
	        User admin = new User("admin", passwordEncoder.encode("admin"), "admin@admin.com",
	        		"admin", "BKDN", "9999999", 1, 1, date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(1));
	        admin.setAuthorities(authorities);
	        userDAO.save(admin);
	    }
	    
	    // User account
	    if (userDAO.findByUsername("user") == null) {
	    	Date date = new Date();
	        User user = new User("user", passwordEncoder.encode("user"), "user@user.com",
	        		"user", "BKDN", "5555555", 1, 1, date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(2));
	        user.setAuthorities(authorities);
	        List<StaffRelationship> staffs = new ArrayList<StaffRelationship>();
	        staffs.add(new StaffRelationship(user, 3));
	        user.setStaffs(staffs);
	        userDAO.save(user);
	    }
	    
	    // Staff account
	    if (userDAO.findByUsername("staff") == null) {
	    	Date date = new Date();
	        User staff = new User("staff", passwordEncoder.encode("staff"), "staff@user.com",
	        		"staff", "BKDN", "3333333", 1, 1, date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(3));
	        staff.setAuthorities(authorities);
	        userDAO.save(staff);
	    }
	}
}
*/