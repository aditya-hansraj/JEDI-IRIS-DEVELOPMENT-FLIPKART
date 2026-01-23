package com.flipfit.core;

import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private String id;
    private String gymMemberId;
    private String slotId;
    private LocalDateTime bookingTime;

    public Booking() {
        // Default constructor for Jackson
    }

    public Booking(String id, String gymMemberId, String slotId, LocalDateTime bookingTime) {
        this.id = id;
        this.gymMemberId = gymMemberId;
        this.slotId = slotId;
        this.bookingTime = bookingTime;
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

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) &&
                Objects.equals(gymMemberId, booking.gymMemberId) &&
                Objects.equals(slotId, booking.slotId) &&
                Objects.equals(bookingTime, booking.bookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gymMemberId, slotId, bookingTime);
    }
}
