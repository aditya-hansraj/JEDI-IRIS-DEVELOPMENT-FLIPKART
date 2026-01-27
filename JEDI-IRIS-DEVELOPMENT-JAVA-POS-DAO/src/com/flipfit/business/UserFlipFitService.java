package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.UserDAOImpl;
import java.util.Optional;

public class UserFlipFitService implements UserFlipFitInterface {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean login(String userName, String password) {
        Optional<User> user = userDAO.findByCredentials(userName, password);
        if (user.isEmpty()) {
            System.out.println("Invalid Credentials.");
            return false;
        }

        if (user.get() instanceof GymOwner) {
            GymOwner owner = (GymOwner) user.get();
            if (!owner.isApproved()) {
                System.out.println("Login Failed: Your account is not yet approved by Admin.");
                return false;
            }
        }
        System.out.println("Login Successful! Welcome " + userName);
        return true;
    }

    @Override
    public void registerCustomer(GymCustomer gymCustomer) {
        ensureUserId(gymCustomer);
        userDAO.saveCustomer(gymCustomer);
        System.out.println("Success: Customer " + gymCustomer.getUserName() + " registered!");
    }

    @Override
    public void registerGymOwner(GymOwner gymOwner) {
        ensureUserId(gymOwner);
        userDAO.saveOwner(gymOwner);
        System.out.println("Success: Gym Owner " + gymOwner.getUserName() + " registered!");
        System.out.println("Status: PENDING APPROVAL (You cannot login yet)");
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        boolean updated = userDAO.changePassword(userName, oldPassword, newPassword);
        if (updated) {
            System.out.println("--------------------------------");
            System.out.println("Success: Password updated for user: " + userName);
            System.out.println("--------------------------------");
            return true;
        }
        System.out.println("Error: The old password you entered is incorrect or user not found.");
        return false;
    }

    private void ensureUserId(User user) {
        if (user.getUserId() == null || user.getUserId().isBlank()) {
            user.setUserId(user.getUserName());
        }
    }
}