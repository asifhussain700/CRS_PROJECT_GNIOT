package com.gniot.crs.exception;

/**
 * Custom exception class for professor-related operations.
 */
public class ProfessorException extends Exception {
    public ProfessorException(String message) {
        super(message);
    }

    public ProfessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
