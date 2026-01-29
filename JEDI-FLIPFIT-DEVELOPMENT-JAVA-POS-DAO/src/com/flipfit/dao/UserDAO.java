package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> findByCredentials(String userName, String password);
    void saveCustomer(GymCustomer gymCustomer);
    void saveOwner(GymOwner gymOwner);
    boolean changePassword(String userName, String oldPassword, String newPassword);
    boolean updateOwnerApproval(String ownerUserName, boolean isApproved);
    List<GymOwner> findPendingOwners();

    /**
     * Returns all gym owners irrespective of their approval status.
     */
    List<GymOwner> findAllOwners();
}
