package com.flipfit.business;

import com.flipfit.bean.FlipFitMockDB;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import java.util.Optional;

public class UserFlipFitService implements UserFlipFitInterface {

    @Override
    public boolean login(String userName, String password) {
        Optional<User> user = FlipFitMockDB.users.stream()
                .filter(u -> u.getUserName().equals(userName) && u.getPassword().equals(password))
                .findFirst();

        if (user.isPresent()) {
            // Check if it is a Gym Owner and if they are approved
            if (user.get() instanceof GymOwner) {
                GymOwner owner = (GymOwner) user.get();
                if (!owner.isApproved()) {
                    System.out.println("Login Failed: Your account is not yet approved by Admin.");
                    return false;
                }
            }
            System.out.println("Login Successful! Welcome " + userName);
            return true;
        } else {
            System.out.println("Invalid Credentials.");
            return false;
        }
    }

    @Override
    public void registerCustomer(GymCustomer gymCustomer) {
        FlipFitMockDB.users.add(gymCustomer);
        System.out.println("Success: Customer " + gymCustomer.getUserName() + " registered!");
    }

    @Override
    public void registerGymOwner(GymOwner gymOwner) {
        // By default, GymOwner isApproved is false (set in Bean constructor)
        FlipFitMockDB.users.add(gymOwner);
        System.out.println("Success: Gym Owner " + gymOwner.getUserName() + " registered!");
        System.out.println("Status: PENDING APPROVAL (You cannot login yet)");
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        // 1. Iterate through users to find the match
        for (User user : FlipFitMockDB.users) {
            if (user.getUserName().equals(userName)) {
                // 2. Check if the old password matches
                if (user.getPassword().equals(oldPassword)) {
                    // 3. Update to new password
                    user.setPassword(newPassword);
                    System.out.println("--------------------------------");
                    System.out.println("Success: Password updated for user: " + userName);
                    System.out.println("--------------------------------");
                    return true;
                } else {
                    System.out.println("Error: The old password you entered is incorrect.");
                    return false;
                }
            }
        }
        System.out.println("Error: User '" + userName + "' not found.");
        return false;
    }
}