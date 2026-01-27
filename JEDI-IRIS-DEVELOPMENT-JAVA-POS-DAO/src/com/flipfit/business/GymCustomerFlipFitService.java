package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.BookingDAOImpl;
import java.util.List;

public class GymCustomerFlipFitService implements GymCustomerFlipFitInterface {
    private final BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    public boolean bookSlot(String bookingId, String slotId, String customerId) {
        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setSlotId(slotId);
        newBooking.setUserId(customerId);
        newBooking.setStatus("Confirmed");

        bookingDAO.save(newBooking);
        System.out.println("Success: Slot " + slotId + " booked! Booking ID: " + bookingId);
        return true;
    }

    @Override
    public boolean cancelBooking(String bookingId) {
        boolean removed = bookingDAO.delete(bookingId);
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
        List<Booking> myBookings = bookingDAO.findByUser(customerId);
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