package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.exception.SlotNotAvailableException;
import java.util.List;

/**
 * Contract for FlipFit operations available to a gym customer.
 *
 * <p>This interface defines booking, cancellation, and viewing
 * capabilities that a customer can perform on centres and slots.</p>
 *
 * @author aditya-hansraj
 */
public interface GymCustomerFlipFitInterface {

    /**
     * Books a slot for the given customer.
     *
     * @param bookingId  unique identifier of the booking
     * @param slotId     identifier of the slot to book
     * @param customerId identifier of the customer performing the booking
     * @return {@code true} if the booking is successfully created
     * @throws SlotNotAvailableException if the slot is not available
     * @throws BookingNotDoneException   if the booking could not be persisted
     */
    boolean bookSlot(String bookingId, String slotId, String customerId)
            throws SlotNotAvailableException, BookingNotDoneException;

    /**
     * Cancels an existing booking.
     *
     * @param bookingId identifier of the booking to cancel
     * @return {@code true} if the booking is successfully cancelled
     * @throws BookingNotDoneException if the cancellation fails or booking is not found
     */
    boolean cancelBooking(String bookingId) throws BookingNotDoneException;

    /**
     * Retrieves all bookings belonging to the given customer.
     *
     * @param customerId identifier of the customer
     * @return list of bookings for that customer; may be empty
     */
    List<Booking> getMyBookings(String customerId);

    /**
     * Retrieves all centres that are currently approved.
     *
     * @return list of approved gym centres; may be empty
     */
    List<GymCentre> getApprovedCentres();

    /**
     * Retrieves all slots available for a specific centre.
     *
     * @param centreId identifier of the centre
     * @return list of slots for the centre; may be empty
     */
    List<Slots> getSlotsForCentre(String centreId);
}