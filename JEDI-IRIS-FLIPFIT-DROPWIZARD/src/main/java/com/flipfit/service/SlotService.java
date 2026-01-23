package com.flipfit.service;

import com.flipfit.core.Slot;
import com.flipfit.db.SlotDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SlotService {
    private final SlotDAO slotDAO;

    public SlotService(SlotDAO slotDAO) {
        this.slotDAO = slotDAO;
    }

    public Optional<Slot> createSlot(String centerId, LocalDateTime startTime, LocalDateTime endTime, int capacity) {
        String id = UUID.randomUUID().toString();
        Slot slot = new Slot(id, centerId, startTime, endTime, capacity);
        try {
            slotDAO.insert(slot);
            return Optional.of(slot);
        } catch (Exception e) {
            // Log exception
            return Optional.empty();
        }
    }

    public Optional<Slot> getSlotById(String id) {
        return slotDAO.findById(id);
    }

    public List<Slot> getSlotsByCenterId(String centerId) {
        return slotDAO.findByCenterId(centerId);
    }

    public List<Slot> getAllSlots() {
        return slotDAO.findAll();
    }

    public void updateSlot(Slot slot) {
        slotDAO.update(slot);
    }

    public void deleteSlot(String id) {
        slotDAO.deleteById(id);
    }

    public boolean addMemberToSlot(String slotId, String gymMemberId) {
        Optional<Slot> slotOpt = slotDAO.findById(slotId);
        if (slotOpt.isPresent()) {
            Slot slot = slotOpt.get();
            if (slot.addMember(gymMemberId)) {
                slotDAO.update(slot);
                return true;
            }
        }
        return false;
    }

    public boolean removeMemberFromSlot(String slotId, String gymMemberId) {
        Optional<Slot> slotOpt = slotDAO.findById(slotId);
        if (slotOpt.isPresent()) {
            Slot slot = slotOpt.get();
            if (slot.removeMember(gymMemberId)) {
                slotDAO.update(slot);
                return true;
            }
        }
        return false;
    }
}
