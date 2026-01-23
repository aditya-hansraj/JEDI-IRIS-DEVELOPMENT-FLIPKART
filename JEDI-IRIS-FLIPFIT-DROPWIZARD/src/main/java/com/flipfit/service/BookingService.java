package com.flipfit.service;

import com.flipfit.core.Booking;
import com.flipfit.core.Slot;
import com.flipfit.db.BookingDAO;
import com.flipfit.db.SlotDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingService {
    private final BookingDAO bookingDAO;
    private final SlotService slotService; // Dependency on SlotService to manage slot capacity

    public BookingService(BookingDAO bookingDAO, SlotService slotService) {
        this.bookingDAO = bookingDAO;
        this.slotService = slotService;
    }

    public Optional<Booking> createBooking(String gymMemberId, String slotId) {
        // First, try to add the member to the slot
        if (slotService.addMemberToSlot(slotId, gymMemberId)) {
            String id = UUID.randomUUID().toString();
            Booking booking = new Booking(id, gymMemberId, slotId, LocalDateTime.now());
            try {
                bookingDAO.insert(booking);
                return Optional.of(booking);
            } catch (Exception e) {
                // If booking insertion fails, try to remove member from slot to revert
                slotService.removeMemberFromSlot(slotId, gymMemberId);
                // Log exception
                return Optional.empty();
            }
        }
        return Optional.empty(); // Slot was full or member already added
    }

    public Optional<Booking> getBookingById(String id) {
        return bookingDAO.findById(id);
    }

    public List<Booking> getBookingsByGymMemberId(String gymMemberId) {
        return bookingDAO.findByGymMemberId(gymMemberId);
    }

    public List<Booking> getBookingsBySlotId(String slotId) {
        return bookingDAO.findBySlotId(slotId);
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }

    public void deleteBooking(String id) {
        // When deleting a booking, also remove the member from the associated slot
        bookingDAO.findById(id).ifPresent(booking -> {
            slotService.removeMemberFromSlot(booking.getSlotId(), booking.getGymMemberId());
            bookingDAO.deleteById(id);
        });
    }
}
