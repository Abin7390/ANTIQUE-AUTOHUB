package com.AntiqueAuto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AntiqueAuto.Entity.users;

@Repository
public interface userRepository extends JpaRepository<users, Long>  {

	users findByEmail(String email);
	users findByid(Long id);
	
}
