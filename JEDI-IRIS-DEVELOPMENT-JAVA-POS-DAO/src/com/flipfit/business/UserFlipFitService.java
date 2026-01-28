package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import com.flipfit.dao.UserDAO;
import com.flipfit.dao.UserDAOImpl;
import com.flipfit.exception.ApprovalPendingException;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.UserNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Service implementation of {@link UserFlipFitInterface}.
 *
 * <p>Coordinates user registration, authentication, and password
 * management using the underlying {@link UserDAO}.</p>
 *
 * @author aditya-hansraj
 */
public class UserFlipFitService implements UserFlipFitInterface {
    private final UserDAO userDAO = new UserDAOImpl();

    /**
     * Login user.
     *
     * @param userName the user name
     * @param password the password
    * @return the authenticated user
     * @throws UserNotFoundException the user not found exception
     * @throws ApprovalPendingException the approval pending exception
     */
    @Override
    public User login(String userName, String password) throws UserNotFoundException, ApprovalPendingException {
    	LocalDate localDate=LocalDate.now();
		LocalTime localTime=LocalTime.now();
        Optional<User> user = userDAO.findByCredentials(userName, password);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Invalid Credentials for user: " + userName);
        }

        if (user.get() instanceof GymOwner) {
            GymOwner owner = (GymOwner) user.get();
            if (!owner.isApproved()) {
                throw new ApprovalPendingException("Login Failed: Your account is not yet approved by Admin.");
            }
        }
        System.out.printf("Login Successful! Welcome " + userName + "%50s%n" + "Logged in at Date: " + localDate + " Time: " + localTime);
        return user.get();
    }

    /**
     * Register customer.
     *
     * @param gymCustomer the gym customer
     * @throws RegistrationNotDoneException the registration not done exception
     */
    @Override
    public void registerCustomer(GymCustomer gymCustomer) throws RegistrationNotDoneException {
        ensureUserId(gymCustomer);
        try {
            userDAO.saveCustomer(gymCustomer);
        } catch (Exception ex) {
            throw new RegistrationNotDoneException("Customer registration failed for user: " + gymCustomer.getUserName(), ex);
        }
        System.out.println("Success: Customer " + gymCustomer.getUserName() + " registered!");
    }

    /**
     * Register gym owner.
     *
     * @param gymOwner the gym owner
     * @throws RegistrationNotDoneException the registration not done exception
     */
    @Override
    public void registerGymOwner(GymOwner gymOwner) throws RegistrationNotDoneException {
        ensureUserId(gymOwner);
        try {
            userDAO.saveOwner(gymOwner);
        } catch (Exception ex) {
            throw new RegistrationNotDoneException("Gym owner registration failed for user: " + gymOwner.getUserName(), ex);
        }
        System.out.println("Success: Gym Owner " + gymOwner.getUserName() + " registered!");
        System.out.println("Status: PENDING APPROVAL (You cannot login yet)");
    }

    /**
     * Change password.
     *
     * @param userName the user name
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true, if successful
     * @throws UserNotFoundException the user not found exception
     */
    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) throws UserNotFoundException {
        boolean updated = userDAO.changePassword(userName, oldPassword, newPassword);
        if (updated) {
            System.out.println("--------------------------------");
            System.out.println("Success: Password updated for user: " + userName);
            System.out.println("--------------------------------");
            return true;
        }
        throw new UserNotFoundException("The old password is incorrect or user not found for: " + userName);
    }

    private void ensureUserId(User user) {
        if (user.getUserId() == null || user.getUserId().isBlank()) {
            user.setUserId(user.getUserName());
        }
    }
}