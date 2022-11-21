package com.vigourhub.backend.infrastructure.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
