package com.AntiqueAuto.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AntiqueAuto.Entity.users;
import com.AntiqueAuto.Repository.userRepository;

@Service
public class userService {

	@Autowired
	public userRepository uRepo; 
	
	public void saveUser(users user) {
		uRepo.save(user); // Save the user to the database
    }
	
	public users authenticateUser(String email, String password) {
        // Find user by username or email
        users user = uRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            // Password match; user authenticated
            return user;
        }
        return null; // Authentication failed
    }
	
	
	public boolean isEmailRegistered(String email) {
        // Check if the email exists in the repository
        return uRepo.findByEmail(email) != null;
    }
}
