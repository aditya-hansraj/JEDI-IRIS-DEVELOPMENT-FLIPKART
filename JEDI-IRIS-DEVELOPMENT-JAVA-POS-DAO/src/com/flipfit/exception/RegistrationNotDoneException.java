package com.flipfit.exception;

/**
 * Thrown when a user or centre registration cannot be completed.
 *
 * @author aditya-hansraj
 */
public class RegistrationNotDoneException extends Exception {
    public RegistrationNotDoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationNotDoneException(String message) {
        super(message);
    }
}
