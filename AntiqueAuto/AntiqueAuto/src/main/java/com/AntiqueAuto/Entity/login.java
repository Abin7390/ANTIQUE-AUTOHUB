package com.AntiqueAuto.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class login {
	
	@Id
    private String email;
    private String password;
    
	public login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public login() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {	
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
