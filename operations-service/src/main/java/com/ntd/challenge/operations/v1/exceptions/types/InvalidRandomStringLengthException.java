package com.ntd.challenge.operations.v1.exceptions.types;

public class InvalidRandomStringLengthException extends InvalidOperationException {

    public InvalidRandomStringLengthException() {
        super("The length must be between 1 and 32");
    }
}
