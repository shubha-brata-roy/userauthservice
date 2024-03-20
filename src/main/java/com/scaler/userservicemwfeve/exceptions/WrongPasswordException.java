package com.scaler.userservicemwfeve.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }
}
