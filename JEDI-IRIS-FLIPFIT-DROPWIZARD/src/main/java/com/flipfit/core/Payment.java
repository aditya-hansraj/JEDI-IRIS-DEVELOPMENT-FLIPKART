package com.flipfit.core;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private String id;
    private String gymMemberId;
    private String slotId;
    private double amount; // Assuming a monetary amount
    private LocalDateTime timestamp;
    private String transactionId; // e.g., from a payment gateway

    public Payment() {
        // Default constructor for Jackson
    }

    public Payment(String id, String gymMemberId, String slotId, double amount, LocalDateTime timestamp, String transactionId) {
        this.id = id;
        this.gymMemberId = gymMemberId;
        this.slotId = slotId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGymMemberId() {
        return gymMemberId;
    }

    public void setGymMemberId(String gymMemberId) {
        this.gymMemberId = gymMemberId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.amount, amount) == 0 &&
                Objects.equals(id, payment.id) &&
                Objects.equals(gymMemberId, payment.gymMemberId) &&
                Objects.equals(slotId, payment.slotId) &&
                Objects.equals(timestamp, payment.timestamp) &&
                Objects.equals(transactionId, payment.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gymMemberId, slotId, amount, timestamp, transactionId);
    }
}
