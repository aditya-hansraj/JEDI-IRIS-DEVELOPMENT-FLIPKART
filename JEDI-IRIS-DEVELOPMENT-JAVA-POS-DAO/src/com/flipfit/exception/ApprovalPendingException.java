package com.flipfit.exception;

/**
 * Thrown when a user attempts an operation while their account
 * is still awaiting administrative approval.
 *
 * @author aditya-hansraj
 */
public class ApprovalPendingException extends Exception {
    public ApprovalPendingException(String message) {
        super(message);
    }
}
