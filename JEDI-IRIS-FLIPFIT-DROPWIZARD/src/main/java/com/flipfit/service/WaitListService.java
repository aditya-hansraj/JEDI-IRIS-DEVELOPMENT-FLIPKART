package com.flipfit.service;

import com.flipfit.core.WaitList;
import com.flipfit.core.Status;
import com.flipfit.core.Slot;
import com.flipfit.db.WaitListDAO;
import com.flipfit.db.SlotDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WaitListService {
    private final WaitListDAO waitListDAO;
    private final SlotDAO slotDAO; // To check slot capacity
    private final BookingService bookingService; // To facilitate booking if slot becomes available

    public WaitListService(WaitListDAO waitListDAO, SlotDAO slotDAO, BookingService bookingService) {
        this.waitListDAO = waitListDAO;
        this.slotDAO = slotDAO;
        this.bookingService = bookingService;
    }

    public Optional<WaitList> addToWaitList(String gymMemberId, String slotId) {
        Optional<Slot> slotOpt = slotDAO.findById(slotId);
        if (slotOpt.isEmpty()) {
            return Optional.empty(); // Slot does not exist
        }

        Slot slot = slotOpt.get();
        if (slot.getRemainingSeats() > 0) {
            // If there are seats, try to book directly instead of waitlisting
            if (bookingService.createBooking(gymMemberId, slotId).isPresent()) {
                // Successfully booked, no need for waitlist
                return Optional.empty();
            }
        }

        // Add to waitlist only if not already booked or on waitlist for this slot
        if (waitListDAO.findBySlotId(slotId).stream().anyMatch(wl -> wl.getGymMemberId().equals(gymMemberId) && wl.getStatus() == Status.PENDING)) {
            return Optional.empty(); // Already on waitlist
        }

        String id = UUID.randomUUID().toString();
        // Determine rank by finding max rank for this slot and adding 1
        int rank = waitListDAO.findBySlotId(slotId).stream()
                .mapToInt(WaitList::getRank)
                .max()
                .orElse(0) + 1;

        WaitList waitListEntry = new WaitList(id, gymMemberId, slotId, rank, Status.PENDING);
        try {
            waitListDAO.insert(waitListEntry);
            return Optional.of(waitListEntry);
        } catch (Exception e) {
            // Log exception
            return Optional.empty();
        }
    }

    public List<WaitList> getWaitListForSlot(String slotId) {
        return waitListDAO.findBySlotId(slotId);
    }

    public void processWaitList(String slotId) {
        List<WaitList> waitList = waitListDAO.findBySlotId(slotId);
        Optional<Slot> slotOpt = slotDAO.findById(slotId);

        if (slotOpt.isEmpty()) {
            return; // Slot does not exist
        }
        Slot slot = slotOpt.get();

        for (WaitList entry : waitList) {
            if (slot.getRemainingSeats() > 0 && entry.getStatus() == Status.PENDING) {
                // Attempt to book for the waitlisted member
                if (bookingService.createBooking(entry.getGymMemberId(), slotId).isPresent()) {
                    entry.setStatus(Status.APPROVED); // Booked from waitlist
                    waitListDAO.update(entry);
                    // In a real app, notify the user
                }
            } else if (slot.getRemainingSeats() == 0) {
                break; // No more seats available
            }
        }
    }

    public void removeMemberFromWaitList(String id) {
        waitListDAO.deleteById(id);
    }
}
