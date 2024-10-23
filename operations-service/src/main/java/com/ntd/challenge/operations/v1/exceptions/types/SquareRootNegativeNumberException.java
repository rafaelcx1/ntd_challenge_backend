package com.ntd.challenge.operations.v1.exceptions.types;

public class SquareRootNegativeNumberException extends InvalidOperationException {

    public SquareRootNegativeNumberException() {
        super("You cannot get the square root of a negative number");
    }
}
