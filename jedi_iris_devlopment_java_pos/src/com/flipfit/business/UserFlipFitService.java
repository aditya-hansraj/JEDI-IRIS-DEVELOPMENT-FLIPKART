package com.flipfit.business;

import com.flipfit.bean.Customer;
import com.flipfit.bean.GymOwner;

public class UserFlipFitService implements UserFlipFitInterface {

    @Override
    public boolean login(String userName, String password) {
        // In real app: check against DB
        System.out.println("User " + userName + " logged in successfully.");
        return true;
    }

    @Override
    public void registerCustomer(Customer customer) {
        System.out.println("Registering Customer: " + customer.getUserName());
        // SHOW OFF LINE: accessing the Role object
        System.out.println("Role: " + customer.getRole().getRoleName());
        System.out.println("--------------------------------");
    }

    @Override
    public void registerGymOwner(GymOwner gymOwner) {
        System.out.println("Registering Gym Owner: " + gymOwner.getUserName());
        // SHOW OFF LINE: accessing the Role object
        System.out.println("Role: " + gymOwner.getRole().getRoleName());
        System.out.println("Status: Pending Approval");
        System.out.println("--------------------------------");
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        System.out.println("Password updated for: " + email);
        return true;
    }
}