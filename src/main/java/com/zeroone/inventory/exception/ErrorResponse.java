package com.zeroone.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Map;

@Getter

public class ErrorResponse {
    private final int status;
    private final String error;
    private final String message;
    private final LocalDateTime timestamp;
    private final Map<String,String> errors;


    public ErrorResponse(int status, String error, String message, LocalDateTime timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = null;
    }


    public ErrorResponse(int status, String error, String message,
                         LocalDateTime timestamp,
                         Map<String, String> errors) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }
}

