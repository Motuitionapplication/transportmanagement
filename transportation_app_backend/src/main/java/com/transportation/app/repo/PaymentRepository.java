package com.transportation.app.repo;

import com.transportation.app.binding.PaymentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentTable, Integer> {
    // Add custom query methods if needed
}
