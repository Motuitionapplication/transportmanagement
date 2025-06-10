package com.transportation.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.PaymentTable;
import com.transportation.app.repo.DriverRepository;
import com.transportation.app.repo.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private DriverRepository driverRepository;
    
    @Override
    @Transactional
    public PaymentTable savePayment(PaymentTable payment) {
        // Handle driver reference first
        if (payment.getDriver() != null) {
            // If driver has username (your join column is on username)
            if (payment.getDriver().getUsername() != null) {
                DriverParameter existingDriver = driverRepository.findByUsername(payment.getDriver().getUsername())
                    .orElseThrow(() -> new RuntimeException("Driver not found with username: " + payment.getDriver().getUsername()));
                payment.setDriver(existingDriver);
            }
            // If driver doesn't have username but has ID
            else if (payment.getDriver().getId() != null) {
                DriverParameter existingDriver = driverRepository.findById(payment.getDriver().getId())
                    .orElseThrow(() -> new RuntimeException("Driver not found with id: " + payment.getDriver().getId()));
                payment.setDriver(existingDriver);
            }
            // If new driver (no username or ID)
            else {
                throw new RuntimeException("Driver must be registered before creating payment");
            }
        }

        // Handle payment save/update
        if (payment.getTransactionId() != null) {
            return paymentRepository.findById(payment.getTransactionId())
                .map(existingPayment -> {
                    // Update existing payment
                    existingPayment.setDriver(payment.getDriver());
                    existingPayment.setDate(payment.getDate());
                    existingPayment.setPickupPin(payment.getPickupPin());
                    existingPayment.setDropPin(payment.getDropPin());
                    existingPayment.setCity(payment.getCity());
                    existingPayment.setDistance(payment.getDistance());
                    existingPayment.setPaymentMode(payment.getPaymentMode());
                    existingPayment.setAmount(payment.getAmount());
                    return paymentRepository.save(existingPayment);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + payment.getTransactionId()));
        }
        return paymentRepository.save(payment);
    }
    @Override
    public Optional<PaymentTable> getPaymentById(Integer transactionId) {
        return paymentRepository.findById(transactionId);
    }
    
    @Override
    public List<PaymentTable> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    @Override
    public void deletePayment(Integer transactionId) {
        paymentRepository.deleteById(transactionId);
    }
    
 
}