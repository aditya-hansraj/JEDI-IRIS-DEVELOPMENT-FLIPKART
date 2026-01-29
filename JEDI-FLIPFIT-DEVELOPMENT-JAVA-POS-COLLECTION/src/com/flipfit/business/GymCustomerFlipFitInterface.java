package com.flipfit.business;

import com.flipfit.bean.Booking;
import java.util.List;

public interface GymCustomerFlipFitInterface {
    boolean bookSlot(String bookingId, String slotId, String customerId);
    boolean cancelBooking(String bookingId);
    List<Booking> getMyBookings(String customerId);
}