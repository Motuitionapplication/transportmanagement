package com.transportation.app.service;

import com.transportation.app.binding.DriverParameter;
import com.transportation.app.binding.PaymentTable;
import com.transportation.app.repo.DriverRepository;
import com.transportation.app.repo.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public PaymentTable savePayment(PaymentTable payment) {
        if (payment.getDriver() != null && payment.getDriver().getUsername() != null) {
            String username = payment.getDriver().getUsername();

            DriverParameter managedDriver = driverRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Driver not found with username: " + username));

            payment.setDriver(managedDriver);

            // ✅ Set Driver First Name from DB
            payment.setDriverName(managedDriver.getFirstName());

            // ✅ Set Vehicle Number from Owner
            if (managedDriver.getOwner() != null) {
                payment.setVehicleNumber(managedDriver.getOwner().getVehicleNumber());
            } else {
                payment.setVehicleNumber("N/A");
            }

        } else {
            throw new RuntimeException("Driver username is missing");
        }

        // Set UUID if not present
        if (payment.getTransactionId() == null) {
            payment.setTransactionId(UUID.randomUUID());
        }

        return paymentRepository.save(payment);
    }


    @Override
    public Optional<PaymentTable> getPaymentById(UUID transactionId) {
        return paymentRepository.findById(transactionId);
    }

    @Override
    public List<PaymentTable> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void deletePayment(UUID transactionId) {
        paymentRepository.deleteById(transactionId);
    }
}
