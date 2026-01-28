package com.flipfit.exception;

/**
 * Thrown when an approval action for an owner or centre fails.
 *
 * @author aditya-hansraj
 */
public class ApprovalNotDoneException extends Exception {
    public ApprovalNotDoneException(String message) {
        super(message);
    }
}
