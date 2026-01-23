package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;

public class UserFlipFitService implements UserFlipFitInterface {

    @Override
    public boolean login(String userName, String password) {
        System.out.println("User " + userName + " logged in successfully.");
        return true;
    }

    @Override
    public void registerCustomer(GymCustomer gymCustomer) {
        System.out.println("Registering Customer: " + gymCustomer.getUserName());
        System.out.println("Role: " + gymCustomer.getRole().getRoleName());
        System.out.println("--------------------------------");
    }

    @Override
    public void registerGymOwner(GymOwner gymOwner) {
        System.out.println("Registering Gym Owner: " + gymOwner.getUserName());
        System.out.println("Role: " + gymOwner.getRole().getRoleName());
        System.out.println("Status: Pending Approval");
        System.out.println("--------------------------------");
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        System.out.println("Verifying old passworchangePasswordd for " + userName + "...");
        System.out.println("Password successfully changed for user: " + userName);
        return true; 
    }
}