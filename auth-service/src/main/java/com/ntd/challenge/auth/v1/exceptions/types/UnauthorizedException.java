package com.ntd.challenge.auth.v1.exceptions.types;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
