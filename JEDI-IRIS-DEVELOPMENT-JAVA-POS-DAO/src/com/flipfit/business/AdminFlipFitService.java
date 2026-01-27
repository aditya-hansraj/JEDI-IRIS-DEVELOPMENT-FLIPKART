package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import com.flipfit.dao.GymCentreDAO;
import com.flipfit.dao.GymCentreDAOImpl;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.UserDAOImpl;
import java.util.List;

public class AdminFlipFitService implements AdminFlipFitInterface {
    private final UserDAO userDAO = new UserDAOImpl();
    private final GymCentreDAO gymCentreDAO = new GymCentreDAOImpl();

    @Override
    public void approveGymOwner(String ownerId, boolean isApproved) {
        boolean updated = userDAO.updateOwnerApproval(ownerId, isApproved);
        if (updated) {
            System.out.println("Action: Gym Owner '" + ownerId + "' has been " + (isApproved ? "APPROVED" : "REJECTED"));
        } else {
            System.out.println("Error: Gym Owner with username '" + ownerId + "' not found.");
        }
    }

    @Override
    public void approveGymCentre(String centreId, boolean isApproved) {
        boolean updated = gymCentreDAO.updateApproval(centreId, isApproved);
        if (updated) {
            System.out.println("Action: Gym Centre '" + centreId + "' has been " + (isApproved ? "APPROVED" : "REJECTED"));
        } else {
            System.out.println("Error: Gym Centre ID '" + centreId + "' not found.");
        }
    }

    @Override
    public List<GymOwner> getPendingOwners() {
        return userDAO.findPendingOwners();
    }

    @Override
    public List<GymCentre> getPendingCentres() {
        return gymCentreDAO.findPendingCentres();
    }
}