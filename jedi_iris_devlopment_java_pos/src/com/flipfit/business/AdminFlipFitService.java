package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import java.util.ArrayList;
import java.util.List;

public class AdminFlipFitService implements AdminFlipFitInterface {

    @Override
    public void approveGymOwner(String ownerId, boolean isApproved) {
        System.out.println("Admin processed Gym Owner approval for ID: " + ownerId + " -> " + (isApproved ? "Approved" : "Rejected"));
    }

    @Override
    public void approveGymCentre(String centreId, boolean isApproved) {
        System.out.println("Admin processed Gym Centre approval for ID: " + centreId + " -> " + (isApproved ? "Approved" : "Rejected"));
    }

    @Override
    public List<GymOwner> getPendingOwners() {
        System.out.println("Fetching pending Gym Owners...");
        return new ArrayList<>();
    }

    @Override
    public List<GymCentre> getPendingCentres() {
        System.out.println("Fetching pending Gym Centres...");
        return new ArrayList<>();
    }
}