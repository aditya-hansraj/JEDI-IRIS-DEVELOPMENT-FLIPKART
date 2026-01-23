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
        // Logic to save customer to DB
    }

    @Override
    public void registerGymOwner(GymOwner gymOwner) {
        System.out.println("Registering Gym Owner: " + gymOwner.getUserName());
        System.out.println("Status: Pending Approval");
        // Logic to save gym owner to DB
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        System.out.println("Password updated for: " + email);
        return true;
    }
}