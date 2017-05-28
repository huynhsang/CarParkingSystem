package com.se52.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name="floor")
public class Floor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "f_column", nullable = false)
	private int column;
	
	@Column(name = "f_row", nullable = false)
	private int row;
	
	@Column(name = "f_level", nullable = false)
	private int level;
	
	@ManyToOne
	@JoinColumn(name = "parking_id")
	private Parking parking;
	
	@OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
	@OrderBy("floor_id ASC, b_row ASC")
	private List<Blank> blanks;
	
	public Floor(){}
	
	public Floor(int row, int column, int level, Parking parking){
		this.row = row;
		this.column = column;
		this.level = level;
		this.parking = parking;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public List<Blank> getBlanks() {
		return blanks;
	}

	public void setBlanks(List<Blank> blanks) {
		this.blanks = blanks;
	}
	
	
}