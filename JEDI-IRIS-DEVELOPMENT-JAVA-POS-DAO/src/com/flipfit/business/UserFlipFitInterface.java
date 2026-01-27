package com.flipfit.business;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.exception.ApprovalPendingException;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.UserNotFoundException;

public interface UserFlipFitInterface {
    // Defines authentication and registration logic
    boolean login(String userName, String password) throws UserNotFoundException, ApprovalPendingException;
    void registerCustomer(GymCustomer gymCustomer) throws RegistrationNotDoneException;
    void registerGymOwner(GymOwner gymOwner) throws RegistrationNotDoneException;
    boolean changePassword(String userName, String oldPassword, String newPassword) throws UserNotFoundException;
}