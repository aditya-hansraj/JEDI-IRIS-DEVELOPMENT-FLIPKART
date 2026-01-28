package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import com.flipfit.exception.ApprovalPendingException;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.UserNotFoundException;

/**
 * User-facing authentication and registration operations for FlipFit.
 *
 * <p>This interface defines login, registration for different roles,
 * and password management.</p>
 *
 * @author aditya-hansraj
 */
public interface UserFlipFitInterface {

    /**
     * Authenticates a user with the given credentials.
     *
     * @param userName the username to authenticate
     * @param password the raw password provided by the user
     * @return the authenticated {@link User}
     * @throws UserNotFoundException    if the user cannot be found or password is invalid
     * @throws ApprovalPendingException if the user exists but is not yet approved
     */
    User login(String userName, String password)
            throws UserNotFoundException, ApprovalPendingException;

    /**
     * Registers a new gym customer.
     *
     * @param gymCustomer customer details to register
     * @throws RegistrationNotDoneException if the registration fails
     */
    void registerCustomer(GymCustomer gymCustomer) throws RegistrationNotDoneException;

    /**
     * Registers a new gym owner.
     *
     * @param gymOwner owner details to register
     * @throws RegistrationNotDoneException if the registration fails
     */
    void registerGymOwner(GymOwner gymOwner) throws RegistrationNotDoneException;

    /**
     * Changes the password for an existing user.
     *
     * @param userName    identifier of the user whose password will be changed
     * @param oldPassword the current password
     * @param newPassword the new password to set
     * @return {@code true} if the password was successfully changed
     * @throws UserNotFoundException if the user cannot be found or old password does not match
     */
    boolean changePassword(String userName, String oldPassword, String newPassword)
            throws UserNotFoundException;
}