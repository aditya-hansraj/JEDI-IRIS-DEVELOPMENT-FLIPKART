package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import com.flipfit.exception.ApprovalNotDoneException;
import java.util.List;

public interface AdminFlipFitInterface {
    void approveGymOwner(String ownerId, boolean isApproved) throws ApprovalNotDoneException;
    void approveGymCentre(String centreId, boolean isApproved) throws ApprovalNotDoneException;
    List<GymOwner> getPendingOwners();
    List<GymCentre> getPendingCentres();
}