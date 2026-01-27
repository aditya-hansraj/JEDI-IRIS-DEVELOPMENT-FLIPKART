package com.flipfit.dao;

import com.flipfit.bean.GymCentre;
import java.util.List;

public interface GymCentreDAO {
    void save(GymCentre centre);
    boolean updateApproval(String centreId, boolean isApproved);
    List<GymCentre> findPendingCentres();
    List<GymCentre> findByOwner(String ownerId);
}
