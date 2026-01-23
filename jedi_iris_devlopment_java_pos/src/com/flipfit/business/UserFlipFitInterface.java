package com.flipfit.business;

import com.flipfit.bean.Customer;
import com.flipfit.bean.GymOwner;

public interface UserFlipFitInterface {
    // Defines authentication and registration logic
    boolean login(String userName, String password);
    void registerCustomer(Customer customer);
    void registerGymOwner(GymOwner gymOwner);
    boolean updatePassword(String email, String newPassword);
}