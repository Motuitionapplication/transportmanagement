package com.transportation.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportation.app.binding.OwnerParameter;

public interface OwnerRepository extends JpaRepository<OwnerParameter, Integer> {
    OwnerParameter findByPassword(String password);
    Optional<OwnerParameter> findByUsername(String username);
}
