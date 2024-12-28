package com.AntiqueAuto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AntiqueAuto.Entity.feedback;

@Repository
public interface feedbackRepository extends JpaRepository<feedback, Long> {

}
