package com.se52.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.se52.entity.Parking;
import com.se52.entity.Ticket;
import com.se52.entity.User;
import com.se52.repository.BlankDAO;
import com.se52.repository.ParkingDAO;
import com.se52.repository.TicketDAO;
import com.se52.repository.UserDAO;
import com.se52.request.UpdateTicketBlank;
import com.se52.util.WebUtil;

@Controller
public class StaffController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	ParkingDAO parkingDAO;
	
	@Autowired
	BlankDAO blankDAO;
	
	@Autowired
	TicketDAO ticketDAO;
	
	@RequestMapping(value = "/staff", method=RequestMethod.GET)
	public @ResponseBody ModelAndView staff (){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User staff = userDAO.findByUsername(userDetails.getUsername());
		Parking p = parkingDAO.findByStaffId(staff.getId());
		List<Ticket> tickets = ticketDAO.findByParkingId(p.getId());
	
		ModelAndView model = new ModelAndView("staff/home");
		model.addObject("staffname", staff.getUsername());
		model.addObject("parking", p);
		model.addObject("p", WebUtil.convertParkingToJson(p));
		model.addObject("tickets", WebUtil.covertTicketsToJson(tickets));
		
		return model;
	}
	
	@RequestMapping(value = "/staff/ticket", method=RequestMethod.POST)
	public @ResponseBody String handleTicket(@RequestBody UpdateTicketBlank data){
		Ticket ticket = data.getTicket();
		ticketDAO.save(ticket);
		blankDAO.save(data.getBlank());
		List<Ticket> tickets = ticketDAO.findByParkingId(ticket.getParkingId());
		return WebUtil.covertTicketsToJson(tickets);
	}
}
