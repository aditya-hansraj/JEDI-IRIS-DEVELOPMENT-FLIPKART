package com.flipfit.core;

import java.time.LocalDateTime;
import java.util.Objects;

public class WaitList {
    private String id;
    private String gymMemberId;
    private String slotId;
    private int rank;
    private Status status; // e.g., PENDING, NOTIFIED, JOINED

    public WaitList() {
        this.status = Status.PENDING; // Default status
    }

    public WaitList(String id, String gymMemberId, String slotId, int rank, Status status) {
        this.id = id;
        this.gymMemberId = gymMemberId;
        this.slotId = slotId;
        this.rank = rank;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGymMemberId() {
        return gymMemberId;
    }

    public void setGymMemberId(String gymMemberId) {
        this.gymMemberId = gymMemberId;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaitList waitList = (WaitList) o;
        return rank == waitList.rank &&
                Objects.equals(id, waitList.id) &&
                Objects.equals(gymMemberId, waitList.gymMemberId) &&
                Objects.equals(slotId, waitList.slotId) &&
                status == waitList.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gymMemberId, slotId, rank, status);
    }
}
