package com.transportation.app.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transportation.app.binding.PaymentTable;
import com.transportation.app.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    
    @Autowired
    private PaymentService paymentService;
    
    @PostMapping("/add")
    public ResponseEntity<?> savePayment(@RequestBody PaymentTable payment) {
        try {
            PaymentTable response = paymentService.savePayment(payment);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .status(HttpStatus.SC_BAD_REQUEST)
                    .body(Map.of(
                        "error", "Payment processing failed",
                        "message", ex.getMessage(),
                        "timestamp", LocalDateTime.now()
                    ));
        }
    }
    // Get a payment by ID
    @GetMapping("/get_by/{id}")
    public ResponseEntity<PaymentTable> getPaymentById(@PathVariable Integer id) {
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
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}