package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.BookingDAOImpl;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.exception.SlotNotAvailableException;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class GymCustomerFlipFitService.
 *
 * @author AI
 * @ClassName "GymCustomerFlipFitService"
 */
public class GymCustomerFlipFitService implements GymCustomerFlipFitInterface {
    private final BookingDAO bookingDAO = new BookingDAOImpl();

    /**
     * Book slot.
     *
     * @param bookingId the booking id
     * @param slotId the slot id
     * @param customerId the customer id
     * @return true, if successful
     * @throws SlotNotAvailableException the slot not available exception
     * @throws BookingNotDoneException the booking not done exception
     */
    @Override
    public boolean bookSlot(String bookingId, String slotId, String customerId) throws SlotNotAvailableException, BookingNotDoneException {
        // Simple availability check placeholder (extend with seats logic when slots DAO exposes it)
        if (slotId == null || slotId.isBlank()) {
            throw new SlotNotAvailableException("Slot not available for booking id: " + bookingId);
        }

        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setSlotId(slotId);
        newBooking.setUserId(customerId);
        newBooking.setStatus("Confirmed");

        try {
            bookingDAO.save(newBooking);
        } catch (Exception ex) {
            throw new BookingNotDoneException("Could not complete booking for slot: " + slotId, ex);
        }
        System.out.println("Success: Slot " + slotId + " booked! Booking ID: " + bookingId);
        return true;
    }

    /**
     * Cancel booking.
     *
     * @param bookingId the booking id
     * @return true, if successful
     * @throws BookingNotDoneException the booking not done exception
     */
    @Override
    public boolean cancelBooking(String bookingId) throws BookingNotDoneException {
        boolean removed;
        try {
            removed = bookingDAO.delete(bookingId);
        } catch (Exception ex) {
            throw new BookingNotDoneException("Could not cancel booking: " + bookingId, ex);
        }
        if (removed) {
            System.out.println("Booking " + bookingId + " cancelled successfully.");
            return true;
        } else {
            throw new BookingNotDoneException("Booking ID not found: " + bookingId);
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