package com.se52.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.se52.entity.Blank;
import com.se52.entity.Floor;
import com.se52.entity.Parking;
import com.se52.entity.Ticket;
import com.se52.entity.User;


public class WebUtil {
	public static User getUserFromSession(HttpServletRequest request){
		try{
			if(request.getSession().getAttribute("user") != null){
				return (User) request.getSession().getAttribute("user");
			}
		}catch(Exception e){
		}
		return null;
	}
	
	public static String convertUsersToJSON(List<User> users){
		JSONArray main = new JSONArray();
		for(User u: users){
			main.put(convertUserToJSON(u));
		}
		return main.toString();
	}
	
	public static String convertUserToJSON(User user){
		JSONObject obj = new JSONObject();
		try{
			obj.put("id", user.getId());
			obj.put("username", user.getUsername());
			obj.put("password", user.getPassword());
			obj.put("email", user.getEmail());
			obj.put("fullname", user.getFullname());
			obj.put("address", user.getAddress());
			obj.put("phone", user.getPhone());
			obj.put("packetID", user.getPacketID());
			obj.put("enabled", user.getEnabled());
			obj.put("lastPasswordResetDate", user.getLastPasswordResetDate());
			
		}catch(JSONException e){
			
		}
		return obj.toString();
	}
	
	public static String converStaffsToJSON(List<User> users, List<Parking> parkings){
		JSONArray main = new JSONArray();
		for(User u: users){
			main.put(converStaffToJSON(u, parkings));
		}
		return main.toString();
	}
	
	public static String converStaffToJSON(User user, List<Parking> parkings){
		JSONObject main = new JSONObject();
		try{
			main.put("id", user.getId());
			main.put("username", user.getUsername());
			main.put("email", user.getEmail());
			main.put("fullname", user.getFullname());
			main.put("address", user.getAddress());
			main.put("phone", user.getPhone());
			main.put("parkingId", "");
			main.put("parkingname", "");
			for(Parking p: parkings){
				if(user.getId() == p.getStaff_id()){
					main.put("parkingId", p.getId());
					main.put("parkingname", p.getName());
				}
			}
			
		}catch(JSONException e){
			
		}
		return main.toString();
	}
	
	public static String covertTicketsToJson(List<Ticket> tickets){
		JSONArray main = new JSONArray();
		for(Ticket t: tickets){
			main.put(convertTicketToJson(t));
		}
		return main.toString();
	}

	public static String convertTicketToJson(Ticket ticket){
		JSONObject main = new JSONObject();
		try{
			main.put("id", ticket.getId());
			main.put("licensePlate", ticket.getLicensePlate());
			main.put("code", ticket.getCode());
			main.put("position", ticket.getPosition());
			main.put("floor", ticket.getFloor());
			main.put("timeFrom", ticket.getTimeFrom());
			main.put("timeTo", ticket.getTimeTo());
			main.put("fee", ticket.getFee());
			main.put("status", ticket.getStatus());
			main.put("parkingId", ticket.getParkingId());
			main.put("staffName", ticket.getStaffName());
		}catch(JSONException e){
			return "";
		}
		return main.toString();
	}
	
	public static String convertParkingToJson(Parking p){
		JSONObject main = new JSONObject();
		JSONArray floors = new JSONArray();
		try{
			for(Floor f: p.getFloors()){
				floors.put(convertFloorToJson(f));
			}
			main.put("id", p.getId());
			main.put("name", p.getName());
			main.put("staff_id", p.getStaff_id());
			main.put("price", p.getPrice());
			main.put("floors", floors);
		}catch(JSONException e){
			return "";
		}catch(Exception e){
			return "";
		}
		return main.toString();
	}
	
	private static String convertFloorToJson(Floor f){
		JSONObject main = new JSONObject();
		JSONArray blanks = new JSONArray();
		try{
			for(Blank b: f.getBlanks()){
				blanks.put(convertBlankToJson(b));
			}
			main.put("id", f.getId());
			main.put("row", f.getRow());
			main.put("column", f.getColumn());
			main.put("level", f.getLevel());
			main.put("blanks", blanks);
		}catch(JSONException e){
			return "";
		}
		return main.toString();
	}
	
	private static String convertBlankToJson(Blank b){
		JSONObject main = new JSONObject();
		try{
			main.put("id", b.getId());
			main.put("row", b.getRow());
			main.put("column", b.getColumn());
			main.put("status", b.getStatus());
		}catch(JSONException e){
			return "";
		}
		
		return main.toString();
	}
}
