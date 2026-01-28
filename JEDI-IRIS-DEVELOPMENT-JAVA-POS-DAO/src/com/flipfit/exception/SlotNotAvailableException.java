package com.flipfit.exception;

/**
 * Thrown when a requested booking slot is unavailable.
 *
 * @author aditya-hansraj
 */
public class SlotNotAvailableException extends Exception {
    public SlotNotAvailableException(String message) {
        super(message);
    }
}
