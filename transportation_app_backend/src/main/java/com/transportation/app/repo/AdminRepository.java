package com.transportation.app.repo;

import java.io.Serializable;
import java.util.List;

import com.transportation.app.binding.Admin;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AdminRepository extends JpaRepository<Admin, Serializable> {
	
	 List<Admin> findByRole(String role);
	Admin findByUsername(String username);



}
