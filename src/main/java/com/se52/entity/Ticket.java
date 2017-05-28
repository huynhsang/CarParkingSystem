package com.se52.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ticket")
public class Ticket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "license_plate", nullable = false)
	private String licensePlate;

	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "position", nullable = false)
	private String position;
	
	@Column(name = "floor", nullable = false)
	private int floor;
	
	@Column(name = "time_from", nullable = false)
	private Date timeFrom;
	
	@Column(name = "time_to")
	private Date timeTo;
	
	@Column(name = "fee", nullable = false)
	private int fee;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "parking_id", nullable = false)
	private int parkingId;
	
	@Column(name = "staff_name", nullable = false)
	private String staffName;
	
	public Ticket(){
	}
	
	public Ticket(String licensePlate, String code, String position, int floor, Date timeFrom, Date timeTo,
			int fee, String status, int parkingId, String staffName){
		this.licensePlate = licensePlate;
		this.code = code;
		this.position = position;
		this.floor = floor;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.fee = fee;
		this.status = status;
		this.parkingId = parkingId;
		this.staffName = staffName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public Date getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Date getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Date timeTo) {
		this.timeTo = timeTo;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getParkingId() {
		return parkingId;
	}

	public void setParkingId(int parkingId) {
		this.parkingId = parkingId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
}
