package com.vigourhub.backend.infrastructure.exceptions;

public class ForbiddenException extends Exception{

    public ForbiddenException(String message) {
        super(message);
    }
}
