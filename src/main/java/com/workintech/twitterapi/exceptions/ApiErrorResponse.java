package com.workintech.twitterapi.exceptions;

import java.time.LocalDateTime;

public class ApiErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ApiErrorResponse(String message, int status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}