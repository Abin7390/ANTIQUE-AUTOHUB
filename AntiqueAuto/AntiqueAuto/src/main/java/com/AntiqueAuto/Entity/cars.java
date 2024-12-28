package com.AntiqueAuto.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;



@Entity 
public class cars {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String owner_name; 
	private String phone_number; // Owner's Phone Number
	private String email; // Owner's Email Address
	private String carNumber;
	private String car_make; // Car Make (e.g., Ford, Chevrolet)
	private String car_model; // Car Model (e.g., Mustang, Camaro)
	private Integer car_year; // Year of Manufacture
	private String car_condition; // Condition (e.g., Excellent, Good, Fair)
	private String car_description; // Car Description
	private BigDecimal starting_bid; // Starting Bid Price
	private String rc; // Image of Vehicle RC (file path or URL)
	private String car_photos; // Car Photos (file path or URL)
	private String status;
	private String fromid;
	private BigDecimal current_bid; // Starting Bid Price
	private String to_id; // Starting Bid Price
	private LocalDateTime auctionEndTime;
	
	
	
	public cars(Long id, String owner_name, String phone_number, String email, String carNumber, String car_make,
			String car_model, Integer car_year, String car_condition, String car_description, BigDecimal starting_bid,
			String rc, String car_photos, String status, String fromid, BigDecimal current_bid, String to_id, LocalDateTime auctionEndTime) {
		super();
		this.id = id;
		this.owner_name = owner_name;
		this.phone_number = phone_number;
		this.email = email;
		this.carNumber = carNumber;
		this.car_make = car_make;
		this.car_model = car_model;
		this.car_year = car_year;
		this.car_condition = car_condition;
		this.car_description = car_description;
		this.starting_bid = starting_bid;
		this.rc = rc;
		this.car_photos = car_photos;
		this.status = status;
		this.fromid = fromid;
		this.current_bid = current_bid;
		this.to_id = to_id;
		this.auctionEndTime = auctionEndTime;
	}
	public cars() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCar_make() {
		return car_make;
	}
	public void setCar_make(String car_make) {
		this.car_make = car_make;
	}
	public String getCar_model() {
		return car_model;
	}
	public void setCar_model(String car_model) {
		this.car_model = car_model;
	}
	public Integer getCar_year() {
		return car_year;
	}
	public void setCar_year(Integer car_year) {
		this.car_year = car_year;
	}
	public String getCar_condition() {
		return car_condition;
	}
	public void setCar_condition(String car_condition) {
		this.car_condition = car_condition;
	}
	public String getCar_description() {
		return car_description;
	}
	public void setCar_description(String car_description) {
		this.car_description = car_description;
	}
	public BigDecimal getStarting_bid() {
		return starting_bid;
	}
	public void setStarting_bid(BigDecimal startingBid) {
		this.starting_bid = startingBid;
	}
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public String getCar_photos() {
		return car_photos;
	}
	public void setCar_photos(String car_photos) {
		this.car_photos = car_photos;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromid() {
		return fromid;
	}
	public void setFromid(String fromid) {
		this.fromid = fromid;
	}
	public BigDecimal getCurrent_bid() {
		return current_bid;
	}
	public void setCurrent_bid(BigDecimal current_bid) {
		this.current_bid = current_bid;
	}
	public String getTo_id() {
		return to_id;
	}
	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}
	public LocalDateTime getAuctionEndTime() {
		return auctionEndTime;
	}
	public void setAuctionEndTime(LocalDateTime auctionEndTime) {
		this.auctionEndTime = auctionEndTime;
	}
	
	
}
