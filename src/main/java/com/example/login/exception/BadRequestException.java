package com.example.login.exception;

public class BedException extends RuntimeException {
    public BedException(String message) {
        super(message);
    }

    public static BedException validateException(String message) {
        return new BedException(message);
    }

    public static BedException activateNoToken() {
        return new BedException("user.activate.no.token");
    }

    public static BedException activateFail() {
        return new BedException("user.activate.fail");
    }

    public static BedException resendActivationNoEmail() {
        return new BedException("user.activation.no.email");
    }

}
