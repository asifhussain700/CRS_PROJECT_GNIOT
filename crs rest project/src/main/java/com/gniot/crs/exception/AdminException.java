package com.gniot.crs.exception;

/**
 * Custom exception class for admin-related operations.
 */
public class AdminException extends Exception {
    public AdminException(String message) {
        super(message);
    }

    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }
}
