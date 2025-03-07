package com.transportation.app.repo;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transportation.app.binding.UserParameter;

public interface UserRepository extends JpaRepository<UserParameter, Serializable> {

    UserParameter findByPassword(String password);

    UserParameter findByUsername(String username);
    
    // Method to find a user by mobile number
    Optional<UserParameter> findByPhone(String phone);
    
    // Updated method: finds a user by email (ignoring case) to check every email available in the email column
    UserParameter findByEmail(String email);
}
