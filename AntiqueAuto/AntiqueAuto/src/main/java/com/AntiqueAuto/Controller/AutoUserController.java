package com.AntiqueAuto.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AntiqueAuto.Entity.cars;
import com.AntiqueAuto.Entity.feedback;
import com.AntiqueAuto.Entity.users;
import com.AntiqueAuto.Repository.carsRepository;
import com.AntiqueAuto.Repository.feedbackRepository;
import com.AntiqueAuto.Repository.userRepository;
import com.AntiqueAuto.Service.carsService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;


@Controller
public class AutoUserController {
	
	@Autowired
	private carsService cService;
	
	@Autowired
    private feedbackRepository feedRepo;
	
	@Autowired
    private userRepository uRepo;
	@Autowired
	private carsRepository cRepo;
	
	@GetMapping("/user_home")
	public String user_home(HttpSession session, Model model) {
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    model.addAttribute("user", user);
		return "user_home.html";
	}
	@GetMapping("/agent_home")
	public String agent_home(HttpSession session, Model model) {
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    model.addAttribute("user", user); // Add the user object to the model
	    return "agent_home"; // Return the name of the view without the .html extension
	}
	
	@GetMapping("/user_bidwon")
	public String user_bidwon(HttpSession session, Model model) {
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    model.addAttribute("user", user); // Add the user object to the model
	    if (user == null) {
	        return "login"; // Redirect to login if the user is not logged in
	    }
	    
	    Long carId = user.getMessage(); // Assuming 'message' contains the carId as Long
	    cars car = cService.getCarById(carId); // Assuming you have a method to get a car by its ID
	    model.addAttribute("car", car);
	    
        return "user_bidwon"; // Adjust to your route
	}
	@GetMapping("/won_recieve")
	public String won_recieve(HttpSession session, Model model) {
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    
	    if (user != null) {
	        // Update the bid status to "received" for this user
	        cService.update_bidStatus(user.getId());
	    }
	    model.addAttribute("user", user); // Add the user object to the model
	    
	    String userType = user.getUser_type(); // Replace with your actual getter for user type

	    
	    if ("user".equals(userType)) {
            // Redirect to a success page or back to the form
            return "redirect:/user_home"; // Adjust to your route
            }
            return "redirect:/agent_home"; // Adjust to your route
	}

	@GetMapping("/user_do")
	public String user_do() {
		return "user_do.html";
	}
	
	@PostMapping("/addcar")
	public String handleFileUpload(
	        @RequestParam("owner_name") String ownerName,
	        @RequestParam("phone_number") String phoneNumber,
	        @RequestParam("email") String email,
	        @RequestParam("carNumber") String carNumber,
	        @RequestParam("car_make") String carMake,
	        @RequestParam("car_model") String carModel,
	        @RequestParam("car_year") Integer carYear,
	        @RequestParam("car_condition") String carCondition,
	        @RequestParam("car_description") String carDescription,
	        @RequestParam("starting_bid") BigDecimal startingBid,
	        @RequestParam("rc") MultipartFile rcFile,
	        @RequestParam("car_photos") MultipartFile[] carPhotos,
	        @RequestParam("status") String status,
	        @RequestParam("fromid") String fromid) {
	    
	    if (!rcFile.isEmpty() || carPhotos.length > 0) {
	        try {
	            // Define file storage directory
	            String uploadDir = "src/main/resources/upload";
	            File uploadPath = new File(uploadDir);

	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs();
	            }

	            // Save RC file to the server
	            String rcFileName = rcFile.getOriginalFilename();
	            java.nio.file.Path rcFilePath = Paths.get(uploadDir, rcFileName);
	            Files.copy(rcFile.getInputStream(), rcFilePath, StandardCopyOption.REPLACE_EXISTING);

	            // Save car photos
	            StringBuilder carPhotosFileNames = new StringBuilder();
	            for (MultipartFile carPhoto : carPhotos) {
	                if (!carPhoto.isEmpty()) {
	                    String carPhotoFileName = carPhoto.getOriginalFilename();
	                    java.nio.file.Path carPhotoFilePath = Paths.get(uploadDir, carPhotoFileName);
	                    Files.copy(carPhoto.getInputStream(), carPhotoFilePath, StandardCopyOption.REPLACE_EXISTING);
	                    carPhotosFileNames.append(carPhotoFileName).append(",");  // Store filenames as a comma-separated string
	                }
	            }

	            // Create and save car entity
	            cars car = new cars();
	            car.setOwner_name(ownerName);
	            car.setCarNumber(phoneNumber);
	            car.setEmail(email);
	            car.setPhone_number(carNumber);
	            car.setCar_make(carMake);
	            car.setCar_model(carModel);
	            car.setCar_year(carYear);
	            car.setCar_condition(carCondition);
	            car.setCar_description(carDescription);
	            car.setStarting_bid(startingBid);
	            car.setRc(rcFileName);  // Save RC file name
	            car.setCar_photos(carPhotosFileNames.toString());  // Save car photos file names
	            car.setStatus(status);
	            car.setFromid(fromid);
	            car.setCurrent_bid(startingBid);
	            car.setTo_id(fromid);

	            // Save car details to the database
	            cService.saveCar(car);

	            return "redirect:/agent_home";
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the error
	            return "redirect:/error";
	        }
	    }
	    // Handle case when no file is uploaded
	    return "redirect:/error";
	}
	
	@GetMapping("/admin_approveCar")
	public String adminApproveCar(@RequestParam(name = "status", required = false) String status, Model model) {
	    List<cars> carsList;

	    // If a status filter is applied, retrieve cars with that status; otherwise, get all cars
	    if (status != null) {
	        carsList = cService.getCarsByStatus(status);
	    } else {
	        carsList = cService.getAllcars();
	    }

	    model.addAttribute("cars", carsList);
	    return "admin_approveCar";
	}

	
	@GetMapping("/admin/approveCar/{carId}")
	public String approveCar(@PathVariable("carId") Long carId) {
	    // Call the service to update the status of the car
	    cService.updateCarStatus(carId, "Approved");
	    return "redirect:/admin_approveCar"; // Redirect back to the list after approval
	}
	@GetMapping("/admin/rejectCar/{carId}")
	public String rejectCar(@PathVariable("carId") Long carId) {
	    // Call the service to update the status of the car
	    cService.updateCarStatus(carId, "Rejected");
	    return "redirect:/admin_approveCar"; // Redirect back to the list after approval
	}

	@GetMapping("/agent_listCars")
	public String myCars(HttpSession session, Model model, @RequestParam(required = false) String status) {
	    users user = (users) session.getAttribute("loggedInUser"); // Get the logged-in user from session
	    model.addAttribute("user", user);
	    if (user == null) {
	        return "login"; // Redirect to login if the user is not logged in
	    }

	    Long fromm = user.getId();
	    String frommString = String.valueOf(fromm);

	    List<cars> userCars;
	    if (status != null) {
	        userCars = cService.getCarByFromIdAndStatus(frommString, status); // Fetch cars based on status
	    } else {
	        userCars = cService.getCarByFromId(frommString); // Fetch all cars if no status is specified
	    }

	    model.addAttribute("userCars", userCars); // Add the cars to the model
	    return "agent_listCars"; // Return the view name without .html
	}

	@GetMapping("/auction1")
	public String auction1(HttpSession session, Model model) {
	    List<cars> ongoingCars = cService.getCarsByStatus("ongoing"); // Fetch all cars with status "ongoing"
	    model.addAttribute("ongoingCars", ongoingCars); // Add the cars to the model
	    
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    model.addAttribute("user", user);
	    if (user == null) {
            return "login"; // Return null to indicate that the redirect has occurred
        }
	    String userType = user.getUser_type(); // Assuming 'usertype' is a field in 'users' class
	    model.addAttribute("usertype", userType);
	    
	    return "auction1"; 
	}

	
	@PostMapping("/update_bid")
	public String updateBid(@RequestParam("car_id") Long carId,
	                        @RequestParam("current_bid") BigDecimal currentBid,
	                        @RequestParam("to_id") String to_id,
	                        RedirectAttributes redirectAttributes) {
	    try {
	        cService.updateBid(carId, currentBid, to_id);
	        redirectAttributes.addFlashAttribute("success", "Bid updated successfully.");
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", "Car not found.");
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());  // Properly handle the exception
	    }
	    return "redirect:/auction1"; // Redirect to the desired page after update
	}
	@GetMapping("/admin_home")
	public String admin_home(HttpSession session, Model model) {
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    model.addAttribute("user", user); // Add the user object to the model
	    return "admin_home"; // Return the name of the view without the .html extension
	}
	@PostMapping("/addToAuction")
	public String addToAuction(@RequestParam("carId") Long carId,
	                           @RequestParam("endDate") LocalDate endDate,
	                           @RequestParam("endTime") LocalTime endTime,
	                           RedirectAttributes redirectAttributes) {
	    try {
	        // Combine the date and time into a single LocalDateTime object
	        LocalDateTime auctionEndTime = LocalDateTime.of(endDate, endTime);

	        // Call the service method to update the auction details
	        cService.startAuction(carId, auctionEndTime);

	        // Set a success message for the user
	        redirectAttributes.addFlashAttribute("success", "Auction started successfully.");
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", "Car not found.");
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());  // Handle exceptions properly
	    }
	    
	    return "redirect:/agent_listCars"; // Redirect to the desired page after the operation
	}
	
	@PostMapping("/endauction")
	public String endAuction(@RequestParam("car_id") Long carId,
	                         @RequestParam("from_id") String fromId,
	                         @RequestParam("to_id") String toId,
	                         RedirectAttributes redirectAttributes) {
	    try {
	        // Finalize the auction by updating the bid and marking the winner
	    	cService.endAuction(carId, fromId, toId);

	        // Send a message to the 'to_id' user indicating they have won
//	    	String message = "Congratulations! You have won the auction for car ID: " + carId + ".";

	        cService.sendMessage(toId, carId);  // Assuming you have a service to send messages

	        redirectAttributes.addFlashAttribute("success", "Auction ended successfully, and the winner has been notified.");
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", "Car not found.");
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());  // Properly handle the exception
	    }
	    return "redirect:/agent_listCars"; // Redirect to the auction page after ending the auction
	}
	
	@GetMapping("/profile")
	public String profile(HttpSession session, Model model) {
	    users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
	    model.addAttribute("user", user);
		return "profile.html";
	}

	
	@PostMapping("/add_feedback")
    public String submitFeedback(@ModelAttribute("feedback") feedback feedback, RedirectAttributes redirectAttributes, HttpSession session) {
		
		users user = (users) session.getAttribute("loggedInUser"); // Replace 'User' with your actual user class
		
		if (user == null) {
	        // Redirect to login page or another appropriate action if user is not logged in
	        redirectAttributes.addFlashAttribute("errorMessage", "You must be logged in to submit feedback.");
	        return "redirect:/login"; // Redirect to your login page
	    }
		
	    String userType = user.getUser_type(); // Replace with your actual getter for user type
	    System.out.println(userType);
        // Save feedback data to the database
		feedRepo.save(feedback);
        
        // Optionally, you can add a success message
        redirectAttributes.addFlashAttribute("successMessage", "Thank you for your feedback!");
        if ("user".equals(userType)) {
        // Redirect to a success page or back to the form
        return "redirect:/user_home"; // Adjust to your route
        }
        return "redirect:/agent_home"; // Adjust to your route

    }
	@PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("userid") Long userid,
    							@RequestParam("username") String username,
                                @RequestParam("email") String email,
                                @RequestParam("contact_number") String contactNumber,
                                @RequestParam("company_name") String companyName,
                                @RequestParam("license_number") String licenseNumber,
                                @RequestParam(value = "password", required = false) String password) {

        // Fetch the user from the database (You may want to implement this more securely)
        users user = uRepo.findByid(userid);

        if (user != null) {
            // Update the user details
            user.setEmail(email);
            user.setContact_number(contactNumber);
            user.setCompany_name(companyName);
            user.setLicense_number(licenseNumber);
            
            // Only update password if it's provided
            if (password != null && !password.isEmpty()) {
                // Here you should hash the password before saving
                user.setPassword(password);
            }

            // Save the updated user back to the database
            uRepo.save(user);
        }
	    String userType = user.getUser_type(); // Replace with your actual getter for user type


        if ("user".equals(userType)) {
            // Redirect to a success page or back to the form
            return "redirect:/user_home"; // Adjust to your route
            }
            return "redirect:/agent_home"; // Adjust to your route
 // Redirect to the profile page after update
    }
	@GetMapping("/user/deleteCar/{carId}")
    public String deleteCar(@PathVariable Long carId, RedirectAttributes redirectAttributes) {
        // Check if car exists before deleting
        if (cRepo.existsById(carId)) {
            cRepo.deleteById(carId);
            redirectAttributes.addFlashAttribute("successMessage", "Car deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Car not found!");
        }
        return "redirect:/agent_listCars"; // Redirect to your desired route after deletion
    }

}
