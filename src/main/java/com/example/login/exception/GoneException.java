package com.example.login.exception;

public class GoneException extends RuntimeException {
    public GoneException(String message) {
        super(message);
    }

    public static GoneException activateTokenExpire() {
        return new GoneException("user.activate.token.expire");
    }

}
