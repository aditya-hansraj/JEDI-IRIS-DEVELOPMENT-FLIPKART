package com.flipfit.service;

import com.flipfit.core.Payment;
import com.flipfit.core.Booking;
import com.flipfit.db.PaymentDAO;
import com.flipfit.db.BookingDAO; // To link payment to a booking if needed

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PaymentService {
    private final PaymentDAO paymentDAO;
    private final BookingDAO bookingDAO; // Optional, for linking payments to bookings

    public PaymentService(PaymentDAO paymentDAO, BookingDAO bookingDAO) {
        this.paymentDAO = paymentDAO;
        this.bookingDAO = bookingDAO;
    }

    public Optional<Payment> processPayment(String gymMemberId, String slotId, double amount) {
        String id = UUID.randomUUID().toString();
        String transactionId = UUID.randomUUID().toString(); // Simulate a transaction ID
        Payment payment = new Payment(id, gymMemberId, slotId, amount, LocalDateTime.now(), transactionId);

        try {
            paymentDAO.insert(payment);
            // Additional logic, e.g., mark booking as paid, send confirmation, etc.
            return Optional.of(payment);
        } catch (Exception e) {
            // Log exception
            return Optional.empty();
        }
    }

    public Optional<Payment> getPaymentById(String id) {
        return paymentDAO.findById(id);
    }

    public List<Payment> getPaymentsByGymMemberId(String gymMemberId) {
        return paymentDAO.findByGymMemberId(gymMemberId);
    }

    public List<Payment> getPaymentsBySlotId(String slotId) {
        return paymentDAO.findBySlotId(slotId);
    }

    public List<Payment> getAllPayments() {
        return paymentDAO.findAll();
    }
}
