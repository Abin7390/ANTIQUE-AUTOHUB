package com.AntiqueAuto.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.AntiqueAuto.Entity.cars;
import com.AntiqueAuto.Entity.users;
import com.AntiqueAuto.Repository.carsRepository;
import com.AntiqueAuto.Repository.userRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class carsService {

    @Autowired
    private carsRepository cRepo; // Autowired repository to handle DB operations
    
    @Autowired
    private userRepository uRepo;
    

    // Save car details in the database
    public void saveCar(cars car) {
        cRepo.save(car);  // Save the car to the database
    }

    // Check if a car is already registered based on car number
    public boolean isCarRegistered(String carNumber) {
        return cRepo.findByCarNumber(carNumber) != null;
    }

	public List<cars> getAllcars() {
		// TODO Auto-generated method stub
		return cRepo.findAll();
	}
	public void updateCarStatus(Long carId, String newStatus) {
	    // Fetch the car from the database using its ID
	    Optional<cars> optionalCar = cRepo.findById(carId);
	    
	    if (optionalCar.isPresent()) {
	        cars car = optionalCar.get();
	        car.setStatus(newStatus);  // Set the new status
	        cRepo.save(car);   // Save the updated car back to the database
	    }
	}
	public List<cars> getCarsByStatus(String status) {
	    return cRepo.findByStatus(status); // Assuming you have a repository method to find by status
	}
	
	public List<cars> getCarByFromId(String fromid) {
        return cRepo.findCarByFromid(fromid); // Query to fetch user-specific cars
    }
	public List<cars> getCarByFromIdAndStatus(String fromid, String status) {
	    return cRepo.findCarByFromidAndStatus(fromid, status); // Query to fetch user-specific cars by status
	}


	public void updateBid(Long carId, BigDecimal newBid, String to_id) {
	    // Fetch the car from the database using its ID
	    Optional<cars> optionalCar = cRepo.findById(carId);

	    if (optionalCar.isPresent()) {
	        cars car = optionalCar.get();
	        BigDecimal currentBid = car.getCurrent_bid();

	        // Check if currentBid is null
	        if (currentBid == null) {
	            // If there's no current bid, accept any new bid
	            car.setCurrent_bid(newBid);
	            car.setTo_id(to_id);
	            cRepo.save(car);  // Save the updated car back to the database
	        } else {
	            // Check if the new bid is greater than the current bid
	            if (newBid.compareTo(currentBid) > 0) {
	                car.setCurrent_bid(newBid);
	                car.setTo_id(to_id);  // Set the new bid
	                cRepo.save(car);  // Save the updated car back to the database
	            } else {
	                throw new IllegalArgumentException("New bid must be greater than the current bid.");
	            }
	        }
	    } else {
	        throw new EntityNotFoundException("Car not found.");
	    }
	}


	public void startAuction(Long carId, LocalDateTime auctionEndTime) {
	    cars car = cRepo.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car not found"));
	    car.setAuctionEndTime(auctionEndTime); // Assuming there's a field for auction end time
	    car.setStatus("ongoing"); // Update the status to "ongoing"
	    cRepo.save(car); // Save the updated car back to the database
	}
	
	public void endAuction(Long carId, String fromId, String toId) {
	    cars car = cRepo.findById(carId)
	            .orElseThrow(() -> new EntityNotFoundException("Car not found"));
	    car.setStatus("sold");
	 // Save car status
	    cRepo.save(car);
	    
	    Long toId_Long = Long.parseLong(toId);
	    
	    users winner = uRepo.findByid(toId_Long);
	    winner.setBid_status("won");
	    
	    uRepo.save(winner);

	    // Logic to mark the car as sold and finalize the auction
	    

	    
	}
	
	public void update_bidStatus(Long userid) {
		users winner = uRepo.findByid(userid);
		winner.setBid_status("recieved");
	    
	    uRepo.save(winner);
		
	}
		

	public void sendMessage(String toId, Long carId) {
		Long toId_Long = Long.parseLong(toId);
	    
	    users winner = uRepo.findByid(toId_Long);		    
	    winner.setMessage(carId);
		    
		    uRepo.save(winner);
		
	}
	
	
	@Scheduled(fixedRate = 60000)  // Runs every 60 seconds (1 minute)
    public void checkAndEndAuctions() {
        LocalDateTime now = LocalDateTime.now();

        // Fetch all cars whose auction end time has passed and are still ongoing
        List<cars> expiredAuctions = cRepo.findByAuctionEndTimeBeforeAndStatus(now, "ongoing");

        for (cars car : expiredAuctions) {
            try {
                // End the auction automatically
                endAuction(car.getId(), car.getFromid(), car.getTo_id());  // Assuming car has fromId and toId
//                String message = "Congratulations! You have won the auction.";
                sendMessage(car.getTo_id(), car.getId());
            } catch (Exception e) {
                System.out.println("Error ending auction for car ID: " + car.getId() + " - " + e.getMessage());
            }
        }
    }

	public cars getCarById(Long carId) {
		// TODO Auto-generated method stub
		return cRepo.findCarById(carId);
	}



	


}
