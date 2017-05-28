package com.se52.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.se52.entity.Blank;
import com.se52.entity.Ticket;

public class UpdateTicketBlank implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Ticket ticket;
	private final Blank blank;
	
	public UpdateTicketBlank(@JsonProperty("ticket") Ticket ticket, @JsonProperty("blank") Blank blank){
		this.ticket = ticket;
		this.blank = blank;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public Blank getBlank() {
		return blank;
	}
	
	
}
