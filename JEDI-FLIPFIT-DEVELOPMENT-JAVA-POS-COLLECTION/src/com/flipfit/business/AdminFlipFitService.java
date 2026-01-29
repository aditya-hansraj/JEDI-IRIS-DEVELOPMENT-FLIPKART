package com.flipfit.business;

import com.flipfit.bean.FlipFitMockDB;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFlipFitService implements AdminFlipFitInterface {

    @Override
    public void approveGymOwner(String ownerId, boolean isApproved) {
        boolean found = false;
        // Search through the User list for a GymOwner with this username
        for (User user : FlipFitMockDB.users) {
            if (user instanceof GymOwner) {
                GymOwner owner = (GymOwner) user;
                if (owner.getUserName().equals(ownerId)) {
                    owner.setApproved(isApproved);
                    found = true;
                    System.out.println("Action: Gym Owner '" + ownerId + "' has been " + (isApproved ? "APPROVED" : "REJECTED"));
                    break;
                }
            }
        }
        if (!found) System.out.println("Error: Gym Owner with username '" + ownerId + "' not found.");
    }

    @Override
    public void approveGymCentre(String centreId, boolean isApproved) {
        boolean found = false;
        for (GymCentre c : FlipFitMockDB.gymCentres) {
            if (c.getCentreId().equals(centreId)) {
                c.setApproved(isApproved);
                found = true;
                System.out.println("Action: Gym Centre '" + c.getCentreName() + "' has been " + (isApproved ? "APPROVED" : "REJECTED"));
                break;
            }
        }
        if (!found) System.out.println("Error: Gym Centre ID '" + centreId + "' not found.");
    }

    @Override
    public List<GymOwner> getPendingOwners() {
        List<GymOwner> pendingList = new ArrayList<>();
        // Filter users to find only unapproved GymOwners
        for (User user : FlipFitMockDB.users) {
            if (user instanceof GymOwner) {
                GymOwner owner = (GymOwner) user;
                if (!owner.isApproved()) {
                    pendingList.add(owner);
                }
            }
        }
        return pendingList;
    }

    @Override
    public List<GymCentre> getPendingCentres() {
        // Filter centres to find only unapproved ones
        return FlipFitMockDB.gymCentres.stream()
                .filter(c -> !c.isApproved())
                .collect(Collectors.toList());
    }
}