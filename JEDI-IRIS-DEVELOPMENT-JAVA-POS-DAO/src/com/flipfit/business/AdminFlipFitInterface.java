package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import com.flipfit.exception.ApprovalNotDoneException;
import java.util.List;

/**
 * Administrative operations available in the FlipFit system.
 *
 * <p>This interface exposes approval workflows for gym owners and
 * centres, as well as queries for pending approvals.</p>
 *
 * @author aditya-hansraj
 */
public interface AdminFlipFitInterface {

    /**
     * Approves or rejects a gym owner registration.
     *
     * @param ownerId    identifier of the gym owner
     * @param isApproved {@code true} to approve, {@code false} to reject
     * @throws ApprovalNotDoneException if the approval action fails
     */
    void approveGymOwner(String ownerId, boolean isApproved) throws ApprovalNotDoneException;

    /**
     * Approves or rejects a gym centre registration.
     *
     * @param centreId   identifier of the gym centre
     * @param isApproved {@code true} to approve, {@code false} to reject
     * @throws ApprovalNotDoneException if the approval action fails
     */
    void approveGymCentre(String centreId, boolean isApproved) throws ApprovalNotDoneException;

    /**
     * Retrieves all gym owners whose registrations are pending approval.
     *
     * @return list of pending gym owners; may be empty
     */
    List<GymOwner> getPendingOwners();

    /**
     * Retrieves all gym centres whose registrations are pending approval.
     *
     * @return list of pending gym centres; may be empty
     */
    List<GymCentre> getPendingCentres();
}