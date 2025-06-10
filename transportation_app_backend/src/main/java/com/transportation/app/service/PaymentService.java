package com.transportation.app.service;

import com.transportation.app.binding.PaymentTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentService {
	public PaymentTable savePayment(PaymentTable payment);

	public Optional<PaymentTable> getPaymentById(Integer transactionId);

	public List<PaymentTable> getAllPayments();

	public void deletePayment(Integer transactionId);
}