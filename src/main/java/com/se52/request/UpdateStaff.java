package com.se52.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.se52.entity.User;

public class UpdateStaff implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int parkingId;
	private final User staff;
	
	public UpdateStaff(@JsonProperty("parkingId") int parkingId,
			@JsonProperty("staff") User staff){
		this.parkingId = parkingId;
		this.staff = staff;
	}

	public int getParkingId() {
		return parkingId;
	}

	public User getStaff() {
		return staff;
	}
	
}
