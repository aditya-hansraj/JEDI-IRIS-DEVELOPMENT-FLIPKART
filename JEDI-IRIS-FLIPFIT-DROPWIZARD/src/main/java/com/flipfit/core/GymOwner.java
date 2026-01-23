package com.flipfit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GymOwner extends User {
    private Status approvalStatus;
    // In a real application, GymCenters would likely be fetched from the database
    // based on the GymOwner's ID, rather than stored directly here.
    // For the model, we can represent it as a list of IDs or a simple placeholder.
    // private List<String> gymCenterIds; // This would be better managed via a service

    public GymOwner() {
        super();
        this.setRole(Role.GYM_OWNER);
        this.approvalStatus = Status.PENDING; // Default status
    }

    public GymOwner(String id, String name, String email, String password, Status approvalStatus) {
        super(id, name, email, password, Role.GYM_OWNER);
        this.approvalStatus = approvalStatus;
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
        if (!super.equals(o)) return false;
        GymOwner gymOwner = (GymOwner) o;
        return approvalStatus == gymOwner.approvalStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), approvalStatus);
    }
}
