package com.ntd.challenge.record.v1.exceptions.types;

public class NotEnoughBalanceException extends Exception {

    public final static String CODE = "NOT_ENOUGH_BALANCE";

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
