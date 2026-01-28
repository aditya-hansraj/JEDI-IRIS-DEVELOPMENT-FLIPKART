package com.flipfit.exception;

/**
 * Thrown when a requested user cannot be located in the system.
 *
 * @author aditya-hansraj
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
