package com.vigourhub.backend.infrastructure.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {

    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ApiError(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public ApiError() {
    }
}
