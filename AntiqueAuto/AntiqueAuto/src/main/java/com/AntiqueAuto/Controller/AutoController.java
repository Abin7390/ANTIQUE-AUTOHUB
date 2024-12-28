package com.AntiqueAuto.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.AntiqueAuto.Entity.users;
import com.AntiqueAuto.Service.userService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AutoController {
	
	@Autowired
	private userService uService;
	
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	@GetMapping("/index")
	public String indexx() {
		return "index.html";
	}
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	@GetMapping("/register")
	public String register() {
		return "register.html";
	}
	@GetMapping("/register_user")
	public String register_user() {
		return "register_user.html";
	}
	@GetMapping("/register_seller")
	public String register_seller(Model model) {
	    model.addAttribute("user", new users()); // Replace 'User' with the actual class name
	    return "register_seller";
	}

	
	
	@PostMapping("/register")
	public String register(@ModelAttribute users user, Model model, @RequestParam("user_type") String user_type) { // Add Model as a parameter
	    try {
	        // Check if the email is already registered
	        if (uService.isEmailRegistered(user.getEmail())) {
	            // If the email is already registered, add an error message to the model
	            model.addAttribute("error", "Email is already registered. Please use a different email.");
	            if ("agent".equalsIgnoreCase(user_type)) {
	            	return "register_seller";
	            }
	            else {
	            return "register_user"; } // Return to the registration page
	        }

	        // Save user details if email is not registered
	        uService.saveUser(user);
	    } catch (Exception e) {
	        System.out.printf("Error saving user: " + e.getMessage(), e);
	        model.addAttribute("error", "An error occurred during registration. Please try again.");
	        return "register"; // Return to the registration page in case of an error
	    }

	    // Redirect to login page or any other page after successful registration
	    return "redirect:/login";
	}

	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, 
	                    @RequestParam("password") String password, 
	                    Model model,
	                    HttpSession session) {
	    try {
	        // Call service to authenticate the user
	        users user = uService.authenticateUser(email, password);
	        if (user != null) {
	            String userType = user.getUser_type();
	            String bid_status = user.getBid_status();
	            System.out.println(bid_status);
	            session.setAttribute("loggedInUser", user); // Store the entire user object in the session
	            
	            if ("agent".equalsIgnoreCase(userType)) {    
	            	if ("won".equalsIgnoreCase(bid_status)) {
		                return "redirect:/user_bidwon"; // Redirect to the user's home page
		            	}
		            	else {
		            	return "redirect:/agent_home";
		            	} // Redirect to the agent's home page
	            } else if ("admin".equalsIgnoreCase(userType)) {
	                return "redirect:/admin_home"; // Redirect to the admin's home page
	            } else {
	            	if ("won".equalsIgnoreCase(bid_status)) {
		                return "redirect:/user_bidwon"; // Redirect to the user's home page
		            	}
		            	else {
		            	return "redirect:/user_home";
		            	} // Redirect to the user's home page
	            }
	        } else {
	            // If authentication fails, return to login page with an error message
	            model.addAttribute("error", "Invalid email or password.");
	            return "login"; // Return to login page
	        }
	    } catch (Exception e) {
	        model.addAttribute("error", "An error occurred during login. Please try again.");
	        return "login"; // Return to login page on exception
	    }
	}

	 
	 @GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        return "redirect:/login";
	    }
	 
	 
	
}
