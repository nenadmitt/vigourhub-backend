package com.vigourhub.backend.infrastructure.exceptions;

public class InternalServerError extends Exception{

    public InternalServerError(String message) {
        super(message);
    }
}
