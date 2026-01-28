package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.exception.BookingNotDoneException;
import com.flipfit.exception.SlotNotAvailableException;
import java.util.List;

public interface GymCustomerFlipFitInterface {
    boolean bookSlot(String bookingId, String slotId, String customerId) throws SlotNotAvailableException, BookingNotDoneException;
    boolean cancelBooking(String bookingId) throws BookingNotDoneException;
    List<Booking> getMyBookings(String customerId);
    List<GymCentre> getApprovedCentres();
    List<Slots> getSlotsForCentre(String centreId);
}