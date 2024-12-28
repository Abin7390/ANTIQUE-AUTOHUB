package com.AntiqueAuto.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.AntiqueAuto.Entity.cars;

@Repository
public interface carsRepository extends JpaRepository<cars, Long> {
    
    // Find car by car number
    cars findByCarNumber(String carNumber);
    List<cars> findByStatus(String status);
	List<cars> findCarByFromid(String fromid);
	@Query("SELECT c FROM cars c WHERE c.fromid = :fromid AND c.status = :status")
	List<cars> findCarByFromidAndStatus(@Param("fromid") String fromid, @Param("status") String status);
	
    List<cars> findByAuctionEndTimeBeforeAndStatus(LocalDateTime now, String status);
	cars findCarById(Long carId);



}

