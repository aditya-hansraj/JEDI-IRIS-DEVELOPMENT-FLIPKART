package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.dao.BookingDAO;
import com.flipfit.dao.BookingDAOImpl;
import com.flipfit.dao.GymCentreDAO;
import com.flipfit.dao.GymCentreDAOImpl;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.SlotDAOImpl;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.exception.SlotNotAvailableException;
import java.util.List;

/**
 * Service implementation of {@link GymCustomerFlipFitInterface} for customers.
 *
 * <p>This class coordinates DAOs to let customers browse centres and slots,
 * book or cancel bookings, and view their booking history.</p>
 *
 * @author aditya-hansraj
 */
public class GymCustomerFlipFitService implements GymCustomerFlipFitInterface {
    private final BookingDAO bookingDAO = new BookingDAOImpl();
    private final GymCentreDAO gymCentreDAO = new GymCentreDAOImpl();
    private final SlotDAO slotDAO = new SlotDAOImpl();

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
        if (slotId == null || slotId.isBlank()) {
            throw new SlotNotAvailableException("Slot not available for booking id: " + bookingId);
        }

        Slots slot = slotDAO.findById(slotId);
        if (slot == null) {
            throw new SlotNotAvailableException("No slot found with ID: " + slotId);
        }
        if (slot.getAvailableSeats() <= 0) {
            throw new SlotNotAvailableException("Slot " + slotId + " is fully booked.");
        }

        boolean seatReserved = slotDAO.reserveSeat(slotId);
        if (!seatReserved) {
            throw new SlotNotAvailableException("Slot " + slotId + " just ran out of seats. Please try another slot.");
        }

        Booking newBooking = new Booking();
        newBooking.setBookingId(bookingId);
        newBooking.setSlotId(slotId);
        newBooking.setUserId(customerId);
        newBooking.setStatus("Confirmed");

        try {
            bookingDAO.save(newBooking);
        } catch (Exception ex) {
            slotDAO.releaseSeat(slotId);
            throw new BookingNotDoneException("Could not complete booking for slot: " + slotId, ex);
        }
        System.out.println("Success: Slot " + slotId + " booked! Booking ID: " + bookingId);
        return true;
    }

    @Override
    public List<GymCentre> getApprovedCentres() {
        List<GymCentre> centres = gymCentreDAO.findApprovedCentres();
        if (centres.isEmpty()) {
            System.out.println("No centres available right now. Please check back later.");
        } else {
            System.out.println("--- APPROVED CENTRES ---");
            for (GymCentre c : centres) {
                System.out.println("ID: " + c.getCentreId() + " | Name: " + c.getCentreName() + " | City: " + c.getCity());
            }
        }
        return centres;
    }

    @Override
    public List<Slots> getSlotsForCentre(String centreId) {
        List<Slots> slots = slotDAO.findByCentre(centreId);
        if (slots.isEmpty()) {
            System.out.println("No slots available for centre: " + centreId);
        } else {
            System.out.println("--- SLOTS FOR CENTRE " + centreId + " ---");
            for (Slots s : slots) {
                System.out.println("Slot ID: " + s.getSlotId() + " | Time: " + s.getStartTime() + " | Available Seats: " + s.getAvailableSeats());
            }
        }
        return slots;
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