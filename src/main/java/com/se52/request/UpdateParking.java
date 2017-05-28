package com.se52.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.se52.entity.Parking;

public class UpdateParking implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int [] idListFloorDeleted;
	private final Parking parking;
	
	public UpdateParking(@JsonProperty("idListFloorDeleted") int[] idListFloorDeleted,
			@JsonProperty("parking") Parking parking){
		this.idListFloorDeleted = idListFloorDeleted;
		this.parking = parking;
	}

	public int[] getIdListFloorDeleted() {
		return idListFloorDeleted;
	}

	public Parking getParking() {
		return parking;
	}
	
	
}
