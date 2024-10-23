package com.ntd.challenge.operations.v1.exceptions.types;

public class DivideByZeroException extends InvalidOperationException {

    public DivideByZeroException() {
        super("You cannot divide a number by zero");
    }
}
