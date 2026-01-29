package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.FlipFitMockDB;
import java.util.List;
import java.util.stream.Collectors;

public class GymCustomerFlipFitService implements GymCustomerFlipFitInterface {

    @Override
    public boolean bookSlot(String bookingId, String slotId, String customerId) {
        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setSlotId(slotId);
        newBooking.setUserId(customerId);
        newBooking.setStatus("Confirmed");

        FlipFitMockDB.bookings.add(newBooking);
        System.out.println("Success: Slot " + slotId + " booked! Booking ID: " + bookingId);
        return true;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        boolean removed = FlipFitMockDB.bookings.removeIf(b -> b.getBookingId().equals(bookingId));
        if (removed) {
            System.out.println("Booking " + bookingId + " cancelled successfully.");
            return true;
        } else {
            System.out.println("Booking ID not found.");
            return false;
        }
    }

    @Override
    public List<Booking> getMyBookings(String customerId) {
        List<Booking> myBookings = FlipFitMockDB.bookings.stream()
                .filter(b -> b.getUserId().equals(customerId))
                .collect(Collectors.toList());

        if (myBookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("--- YOUR BOOKINGS ---");
            for (Booking b : myBookings) {
                System.out.println("Booking ID: " + b.getBookingId() + " | Slot ID: " + b.getSlotId());
            }
        }
        return myBookings;
    }
}