package com.transportation.app.repo;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transportation.app.binding.OwnerParameter;

public interface OwnerRepository extends JpaRepository<OwnerParameter, Integer> {
    OwnerParameter findByPassword(String password);
    OwnerParameter findByUsername(String username);
}
