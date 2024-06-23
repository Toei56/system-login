package com.example.login.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException unauthorized() {
        return new UnauthorizedException("user.unauthorized");
    }

    public static UnauthorizedException loginFailPasswordIncorrect() {
        return new UnauthorizedException("user.login.fail");
    }


}
