package com.flipfit.business;

import com.flipfit.bean.Booking;
import java.util.ArrayList;
import java.util.List;

public class GymCustomerFlipFitService implements GymCustomerFlipFitInterface {

    @Override
    public boolean bookSlot(String bookingId, String slotId, String customerId) {
        System.out.println("Slot booked for customer: " + customerId);
        return true;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        System.out.println("Booking cancelled: " + bookingId);
        return true;
    }

    @Override
    public List<Booking> getMyBookings(String customerId) {
        System.out.println("Fetching bookings for customer: " + customerId);
        return new ArrayList<>();
    }
}