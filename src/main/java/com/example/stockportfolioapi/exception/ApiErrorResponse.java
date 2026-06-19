package com.example.stockportfolioapi.exception;

import java.time.LocalDateTime;

/**
 * Represents a clean error response returned by the REST API.
 * This class is used when something goes wrong, for example when a stock is not found.
 */
public class ApiErrorResponse {

    /**
     * Date and time when the error happened.
     */
    private LocalDateTime timestamp;

    /**
     * HTTP status code, for example 404 or 400.
     */
    private int status;

    /**
     * Short HTTP error name, for example Not Found or Bad Request.
     */
    private String error;

    /**
     * Human-readable explanation of the error.
     */
    private String message;

    /**
     * API path where the error happened.
     */
    private String path;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}