package com.transportation.app.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transportation.app.binding.Transport;

public interface TransportRepository extends JpaRepository<Transport, Serializable> {

}
 