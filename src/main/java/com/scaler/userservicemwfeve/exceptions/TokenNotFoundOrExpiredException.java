package com.scaler.userservicemwfeve.exceptions;

public class TokenNotFoundOrExpiredException extends Exception {
    public TokenNotFoundOrExpiredException(String message) {
        super(message);
    }
}
