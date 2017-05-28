package com.se52.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se52.entity.Authority;
import com.se52.entity.Blank;
import com.se52.entity.Floor;
import com.se52.entity.Parking;
import com.se52.entity.StaffRelationship;
import com.se52.entity.Ticket;
import com.se52.entity.User;
import com.se52.repository.AuthorityDAO;
import com.se52.repository.BlankDAO;
import com.se52.repository.FloorDAO;
import com.se52.repository.ParkingDAO;
import com.se52.repository.StaffRelationshipDAO;
import com.se52.repository.TicketDAO;
import com.se52.repository.UserDAO;
import com.se52.request.UpdateParking;
import com.se52.request.UpdateStaff;
import com.se52.util.WebUtil;

@Controller
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ParkingDAO parkingDAO;
	
	@Autowired
	FloorDAO floorDAO;
	
	@Autowired
	BlankDAO blankDAO;
	
	@Autowired
	StaffRelationshipDAO staffRelationshipDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityDAO authDAO;
	
	@Autowired
	private TicketDAO ticketDAO;
	
	
	@RequestMapping(value = "/user", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
	public @ResponseBody ModelAndView user (){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUsername(userDetails.getUsername());
		
		ModelAndView model = new ModelAndView("user/home");
		model.addObject("parkings", user.getParkings());
		return model;
	}
	
	@RequestMapping(value = "/user/parking-diary", method=RequestMethod.GET)
	public @ResponseBody ModelAndView parkingDiary(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUsername(userDetails.getUsername());
		List<Parking> parkings = parkingDAO.findByUserId(user.getId());
		List<Ticket> tickets = new ArrayList<Ticket>();
		for(Parking p: parkings){
			List<Ticket> temp = ticketDAO.findCompletedByParkingId(p.getId());
			tickets.addAll(temp);
		}
		ModelAndView model = new ModelAndView("user/parkingDiary");
		model.addObject("parkings", parkings);
		model.addObject("tickets", WebUtil.covertTicketsToJson(tickets));
		return model;
	}
	
	@RequestMapping(value = "/user/revenue", method=RequestMethod.GET)
	public @ResponseBody ModelAndView userRevenue (){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUsername(userDetails.getUsername());
		List<Parking> parkings = parkingDAO.findByUserId(user.getId());
		List<Ticket> tickets = new ArrayList<Ticket>();
		for(Parking p: parkings){
			List<Ticket> temp = ticketDAO.findCompletedByParkingId(p.getId());
			tickets.addAll(temp);
		}
		ModelAndView model = new ModelAndView("user/revenue");
		model.addObject("parking", parkings.get(0));
		model.addObject("tickets", WebUtil.covertTicketsToJson(tickets));
		
		return model;
	}
	
	@RequestMapping(value = "/user/revenue/update", method=RequestMethod.POST)
	public @ResponseBody String userRevenueUpdate (@RequestBody int price){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUsername(userDetails.getUsername());
		List<Parking> parkings = parkingDAO.findByUserId(user.getId());
		for(Parking p: parkings){
			p.setPrice(price);
			parkingDAO.save(p);
		}
		return "success";
	}
	
	@RequestMapping(value = "/user/staff-management", method=RequestMethod.GET)
	public @ResponseBody ModelAndView staffManagement (){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUsername(userDetails.getUsername());
		List<StaffRelationship> listStaff = staffRelationshipDAO.findByUserId(user.getId());
		List<Integer> ids = new ArrayList<Integer>();
		for(StaffRelationship item: listStaff){
			ids.add(item.getStaffId());
		}
		List<User> staffs = userDAO.findByListUserId(ids);
		List<Parking> parkings = parkingDAO.findByUserId(user.getId());
		ModelAndView model = new ModelAndView("user/staffManagement");
		model.addObject("staffs", WebUtil.converStaffsToJSON(staffs, parkings));
		model.addObject("parkings", parkings);
		return model;
	}
	
	@RequestMapping(value = "/user/staff/update", method=RequestMethod.POST)
	public @ResponseBody String updateStaff(@RequestBody UpdateStaff data){
		User dataStaff = data.getStaff();
		if(dataStaff.getId() != 0){
			User staff = userDAO.findOne(dataStaff.getId());
			staff.setFullname(dataStaff.getFullname());
			staff.setUsername(dataStaff.getUsername());
			staff.setEmail(dataStaff.getEmail());
			staff.setPhone(dataStaff.getPhone());
			staff.setAddress(dataStaff.getAddress());
			if(dataStaff.getPassword().trim() != ""){
				staff.setPassword(passwordEncoder.encode(dataStaff.getPassword()));
			}
			if(data.getParkingId() != 0){
				Parking p = parkingDAO.findOne(data.getParkingId());
				p.setStaff_id(dataStaff.getId());
				parkingDAO.save(p);
			}
	        userDAO.save(staff);
		}else{
			Date date = new Date();
	        User staff = new User(dataStaff.getUsername(), passwordEncoder.encode(dataStaff.getPassword()), dataStaff.getEmail(),
	        		dataStaff.getFullname(), dataStaff.getAddress(), dataStaff.getPhone(), 0, 1, date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(3));
	        staff.setAuthorities(authorities);
	        userDAO.save(staff);
	        
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDAO.findByUsername(userDetails.getUsername());
			List<StaffRelationship> relationship = user.getStaffs();
			int id = userDAO.findByUsername(dataStaff.getUsername()).getId();
			relationship.add(new StaffRelationship(user, id));
			Parking p = parkingDAO.findOne(data.getParkingId());
			p.setStaff_id(id);
			parkingDAO.save(p);
			userDAO.save(user);
		}
		
		return "";
	}
	
	@RequestMapping(value = "/user/staff/{id}", method=RequestMethod.DELETE)
	public @ResponseBody String deleteStaff(@PathVariable int id){
		userDAO.delete(id);
		return "success";
	}
	
	@RequestMapping(value = "/user/parking/{id}", method=RequestMethod.GET)
	public @ResponseBody ModelAndView parkingDetail(@PathVariable int id){
		Parking p = parkingDAO.findOne(id);
		if(p == null){
			return new ModelAndView("redirect:/");
		}
		ModelAndView model = new ModelAndView("user/parkingDetail");
		model.addObject("parking", p);
		model.addObject("p", WebUtil.convertParkingToJson(p));
		return model;
		
	}
	
	
	@RequestMapping(value = "/user/parking/create", method=RequestMethod.POST)
	public @ResponseBody String createParking(@RequestBody Parking parking){
		parking.setPrice(10000);
		saveParking(parking);
		return "success";
	}
	
	
	@RequestMapping(value = "/user/parking/update", method=RequestMethod.POST)
	public @ResponseBody String updateParking(@RequestBody UpdateParking update){
		int [] ids = update.getIdListFloorDeleted();
		for(int i=0; i<ids.length; i++){
			floorDAO.delete(ids[i]);
		}
		saveParking(update.getParking());
		return "success";
	}
	
	@RequestMapping(value = "/user/floor/update", method=RequestMethod.POST)
	public @ResponseBody String updateFloor(@RequestBody Floor floor){
		floorDAO.save(floor);
		return "success";
	}
	
	@RequestMapping(value = "/user/blank/update", method=RequestMethod.POST)
	public @ResponseBody String updateBlank(@RequestBody Blank blank){
		blankDAO.save(blank);
		return "success";
	}
	
	
	@RequestMapping(value = "/user/parking/{id}", method=RequestMethod.DELETE)
	public @ResponseBody String deleteParking(@PathVariable int id){
		parkingDAO.delete(id);
		return "success";
	}
	
	private void saveParking(Parking parking){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDAO.findByUsername(userDetails.getUsername());
		parking.setUser(user);
		for(Floor item: parking.getFloors()){
			item.setParking(parking);
			for(Blank blank: item.getBlanks()){
				blank.setFloor(item);
			}
		}
		parkingDAO.save(parking);
	}
}
