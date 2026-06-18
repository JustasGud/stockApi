package com.example.stockportfolioapi.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new exception with a custom message.
     *
     * @param message explanation of what was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}