package com.transportation.app.repo;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transportation.app.binding.DriverParameter;

public interface DriverRepository extends JpaRepository<DriverParameter, Serializable> {
	
	DriverParameter findByPassword(String password);

	DriverParameter findByUsername(String username);
    
}
