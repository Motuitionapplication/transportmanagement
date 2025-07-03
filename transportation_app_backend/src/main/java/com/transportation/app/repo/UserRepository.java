package com.transportation.app.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportation.app.binding.User;

public interface UserRepository extends JpaRepository<User, Serializable> {
	
	 List<User> findByRole(String role);
	User findByUsername(String username);



}
