package com.transportation.app.rest;

import com.transportation.app.binding.PaymentTable;
import com.transportation.app.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Save a new payment with basic error handling
    @PostMapping("/add")
    public ResponseEntity<?> savePayment(@RequestBody PaymentTable payment) {
        try {
            PaymentTable response = paymentService.savePayment(payment);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(500)
                    .body("Error while saving payment: " + ex.getMessage());
        }
    }

    // Get a payment by ID
    @GetMapping("/get_by/{id}")
    public ResponseEntity<PaymentTable> getPaymentById(@PathVariable UUID id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all payments
    @GetMapping("/get_all")
    public ResponseEntity<List<PaymentTable>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // Delete payment by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
