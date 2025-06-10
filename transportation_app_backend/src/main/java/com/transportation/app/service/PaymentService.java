package com.transportation.app.service;

import com.transportation.app.binding.PaymentTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentService {
	
	PaymentTable savePayment(PaymentTable payment);

	Optional<PaymentTable> getPaymentById(UUID transactionId);

	List<PaymentTable> getAllPayments();

	void deletePayment(UUID transactionId);
}
