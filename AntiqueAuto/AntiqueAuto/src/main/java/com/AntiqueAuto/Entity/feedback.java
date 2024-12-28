package com.AntiqueAuto.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long feedbackid;
	private String fullname; 
	private String phoneNumber; 
	private String emailAddress; 
	private String message;
	public feedback(Long feedbackid, String fullname, String phoneNumber, String emailAddress, String message) {
		super();
		this.feedbackid = feedbackid;
		this.fullname = fullname;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.message = message;
	}
	public feedback() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getFeedbackid() {
		return feedbackid;
	}
	public void setFeedbackid(Long feedbackid) {
		this.feedbackid = feedbackid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 

}
