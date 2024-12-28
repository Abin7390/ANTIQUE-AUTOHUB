package com.AntiqueAuto.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String username;
	private String email;
	private String contact_number;
	private String password;
	private String company_name;
	private String license_number;
	private String user_type;
	private String bid_status;
	private Long message;
	public users(Long id, String username, String email, String contact_number, String password, String company_name,
			String license_number, String user_type, String bid_status, Long message) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.contact_number = contact_number;
		this.password = password;
		this.company_name = company_name;
		this.license_number = license_number;
		this.user_type = user_type;
		this.bid_status = bid_status;
		this.message = message;
	}
	public users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getLicense_number() {
		return license_number;
	}
	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getBid_status() {
		return bid_status;
	}
	public void setBid_status(String bid_status) {
		this.bid_status = bid_status;
	}
	public Long getMessage() {
		return message;
	}
	public void setMessage(Long message) {
		this.message = message;
	}
	
	

}
