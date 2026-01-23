package com.flipfit.core;

import java.util.Objects;
import java.util.List; // Assuming a list of Slot IDs or Slot objects
import java.util.ArrayList; // For initialization

public class Center {
    private String id;
    private String name;
    private String address;
    private String gymOwnerId; // To link a center to its owner
    private Status approvalStatus;
    // In a real application, slots would typically be fetched via a SlotDAO
    // private List<Slot> slots; // This could be a list of Slot objects or just IDs

    public Center() {
        // Default constructor for Jackson
        this.approvalStatus = Status.PENDING; // Default status
    }

    public Center(String id, String name, String address, String gymOwnerId, Status approvalStatus) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gymOwnerId = gymOwnerId;
        this.approvalStatus = approvalStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGymOwnerId() {
        return gymOwnerId;
    }

    public void setGymOwnerId(String gymOwnerId) {
        this.gymOwnerId = gymOwnerId;
    }

    public Status getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Status approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Center center = (Center) o;
        return Objects.equals(id, center.id) &&
                Objects.equals(name, center.name) &&
                Objects.equals(address, center.address) &&
                Objects.equals(gymOwnerId, center.gymOwnerId) &&
                approvalStatus == center.approvalStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, gymOwnerId, approvalStatus);
    }
}
