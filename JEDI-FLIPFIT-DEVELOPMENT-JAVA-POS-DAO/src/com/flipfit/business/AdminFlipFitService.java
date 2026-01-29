package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import com.flipfit.dao.GymCentreDAO;
import com.flipfit.dao.GymCentreDAOImpl;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.UserDAOImpl;
import com.flipfit.exception.ApprovalNotDoneException;
import java.util.Comparator;
import java.util.List;

/**
 * Service implementation of {@link AdminFlipFitInterface}.
 *
 * <p>Handles approval workflows for gym owners and gym centres by
 * delegating to the appropriate DAOs.</p>
 *
 * @author aditya-hansraj
 */
public class AdminFlipFitService implements AdminFlipFitInterface {
    private final UserDAO userDAO = new UserDAOImpl();
    private final GymCentreDAO gymCentreDAO = new GymCentreDAOImpl();

    /**
     * Approve gym owner.
     *
     * @param ownerId the owner id
     * @param isApproved the is approved
     * @throws ApprovalNotDoneException the approval not done exception
     */
    @Override
    public void approveGymOwner(String ownerId, boolean isApproved) throws ApprovalNotDoneException {
        boolean updated = userDAO.updateOwnerApproval(ownerId, isApproved);
        if (updated) {
            System.out.println("Action: Gym Owner '" + ownerId + "' has been " + (isApproved ? "APPROVED" : "REJECTED"));
        } else {
            throw new ApprovalNotDoneException("Gym Owner with username '" + ownerId + "' not found.");
        }
    }

    /**
     * Approve gym centre.
     *
     * @param centreId the centre id
     * @param isApproved the is approved
     * @throws ApprovalNotDoneException the approval not done exception
     */
    @Override
    public void approveGymCentre(String centreId, boolean isApproved) throws ApprovalNotDoneException {
        boolean updated = gymCentreDAO.updateApproval(centreId, isApproved);
        if (updated) {
            System.out.println("Action: Gym Centre '" + centreId + "' has been " + (isApproved ? "APPROVED" : "REJECTED"));
        } else {
            throw new ApprovalNotDoneException("Gym Centre ID '" + centreId + "' not found.");
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

    @Override
    public List<GymOwner> getApprovedOwners() {
        return userDAO.findAllOwners().stream()
                .filter(GymOwner::isApproved)
                .sorted(Comparator.comparing(GymOwner::getUserName))
                .toList();
    }

    @Override
    public List<GymOwner> getNotApprovedOwners() {
        return userDAO.findAllOwners().stream()
                .filter(owner -> !owner.isApproved())
                .sorted(Comparator.comparing(GymOwner::getUserName))
                .toList();
    }

    @Override
    public List<GymCentre> getApprovedCentres() {
        return gymCentreDAO.findAllCentres().stream()
                .filter(GymCentre::isApproved)
                .sorted(Comparator.comparing(GymCentre::getCentreName))
                .toList();
    }

    @Override
    public List<GymCentre> getNotApprovedCentres() {
        return gymCentreDAO.findAllCentres().stream()
                .filter(centre -> !centre.isApproved())
                .sorted(Comparator.comparing(GymCentre::getCentreName))
                .toList();
    }
}