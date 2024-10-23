package com.ntd.challenge.auth.v1.exceptions.types;

public class ExistentUserException extends RuntimeException {

    public ExistentUserException(String message) {
        super(message);
    }
}
