package com.transportation.app.repo;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportation.app.binding.CustomerParameter;

public interface CustomerRepository extends JpaRepository<CustomerParameter, Serializable> {

	CustomerParameter findByPassword(String password);

	CustomerParameter findByUsername(String username);
    
    Optional<CustomerParameter> findByPhone(String phone);
    
    CustomerParameter findByEmail(String email);
}
