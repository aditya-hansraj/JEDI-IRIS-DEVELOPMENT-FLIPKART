package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.GymOwner;
import java.util.List;

public interface AdminFlipFitInterface {
    void approveGymOwner(String ownerId, boolean isApproved);
    void approveGymCentre(String centreId, boolean isApproved);
    List<GymOwner> getPendingOwners();
    List<GymCentre> getPendingCentres();
}