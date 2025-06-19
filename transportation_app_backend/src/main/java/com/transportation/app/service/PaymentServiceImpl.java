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
        // If driverUsername was set via JSON, resolve the full driver entity
        if (payment.getFk_driver() != null && payment.getFk_driver().getUsername() != null) {
            String username = payment.getFk_driver().getUsername();
            DriverParameter existingDriver = driverRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Driver not found with username: " + username));
            payment.setFk_driver(existingDriver);
        } else {
            throw new RuntimeException("Driver username must be provided in the request");
        }

        // Update existing payment if transactionId is present
        if (payment.getTransactionId() != null) {
            return paymentRepository.findById(payment.getTransactionId())
                .map(existingPayment -> {
                    existingPayment.setFk_driver(payment.getFk_driver());
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

        // Save new payment
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
