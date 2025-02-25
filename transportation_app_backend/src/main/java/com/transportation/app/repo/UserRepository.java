package com.transportation.app.repo;

import java.io.Serializable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transportation.app.binding.UserParameter;

public interface UserRepository extends JpaRepository<UserParameter, Serializable> {

    UserParameter findByPassword(String password);

    UserParameter findByEmail(String email);
    
    // Added method to find a user by mobile number
    Optional<UserParameter> findByPhone(String phone);
}
