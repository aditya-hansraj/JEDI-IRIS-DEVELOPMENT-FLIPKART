package com.flipfit.exception;

/**
 * Thrown when a booking operation cannot be completed successfully.
 *
 * @author aditya-hansraj
 */
public class BookingNotDoneException extends Exception {
    public BookingNotDoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookingNotDoneException(String message) {
        super(message);
    }
}
