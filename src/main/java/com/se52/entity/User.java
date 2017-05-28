package com.se52.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", nullable = false)
	private int id;
	
	@Column(name="username", nullable = false)
	private String username;
	
	@Column(name="password", nullable = false)
	private String password;
	
	@Column(name="email", nullable = false)
	private String email;
	
	@Column(name="fullname", nullable = false)
	private String fullname;
	
	@Column(name="address", nullable = false)
	private String address;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="packet_id")
	private int packetID;
	
	@Column(name="enabled")
	private int enabled;
	
	@Column(name="last_password_reset_date")
	private Date lastPasswordResetDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="user_role",
			joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="authority_id", referencedColumnName="id")})
	private List<Authority> authorities;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<StaffRelationship> staffs;
	
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Parking> parkings;
	
	public User(){}
	
	public User(String username, String password, String email, String fullname,
			String address, String phone, int packetID, int enabled, Date lastPasswordResetDate){
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.packetID = packetID;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	
	
	
	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getPacketID() {
        return packetID;
    }

    public void setPacketID(int packetID) {
        this.packetID = packetID;
    }

    public Boolean getEnabled() {
    	if(enabled == 1) return true;
    	else return false;
    }

    public void setEnabled(Boolean enabled) {
    	if(enabled) this.enabled = 1;
    	else this.enabled = 0;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

	public List<StaffRelationship> getStaffs() {
		return staffs;
	}

	public void setStaffs(List<StaffRelationship> staffs) {
		this.staffs = staffs;
	}

	public List<Parking> getParkings() {
		return parkings;
	}

	public void setParkings(List<Parking> parkings) {
		this.parkings = parkings;
	}
    
    
}
