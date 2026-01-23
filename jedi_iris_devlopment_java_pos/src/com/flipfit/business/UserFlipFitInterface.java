package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;

public interface UserFlipFitInterface {
    // Defines authentication and registration logic
    boolean login(String userName, String password);
    void registerCustomer(GymCustomer gymCustomer);
    void registerGymOwner(GymOwner gymOwner);
    boolean changePassword(String userName, String oldPassword, String newPassword);
}