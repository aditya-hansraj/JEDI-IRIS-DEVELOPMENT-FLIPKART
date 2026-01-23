package com.flipfit.core;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;

public class Slot {
    private String id;
    private String centerId; // Link to the Center
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int capacity;
    private List<String> bookedMemberIds; // List of GymMember IDs who booked this slot

    public Slot() {
        this.bookedMemberIds = new ArrayList<>();
    }

    public Slot(String id, String centerId, LocalDateTime startTime, LocalDateTime endTime, int capacity) {
        this.id = id;
        this.centerId = centerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.bookedMemberIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<String> getBookedMemberIds() {
        return bookedMemberIds;
    }

    public void setBookedMemberIds(List<String> bookedMemberIds) {
        this.bookedMemberIds = bookedMemberIds;
    }

    public int getRemainingSeats() {
        return capacity - bookedMemberIds.size();
    }

    public boolean addMember(String gymMemberId) {
        if (bookedMemberIds.size() < capacity && !bookedMemberIds.contains(gymMemberId)) {
            bookedMemberIds.add(gymMemberId);
            return true;
        }
        return false;
    }

    public boolean removeMember(String gymMemberId) {
        return bookedMemberIds.remove(gymMemberId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return capacity == slot.capacity &&
                Objects.equals(id, slot.id) &&
                Objects.equals(centerId, slot.centerId) &&
                Objects.equals(startTime, slot.startTime) &&
                Objects.equals(endTime, slot.endTime) &&
                Objects.equals(bookedMemberIds, slot.bookedMemberIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, centerId, startTime, endTime, capacity, bookedMemberIds);
    }
}
