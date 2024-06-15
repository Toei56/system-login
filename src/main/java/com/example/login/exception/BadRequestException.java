package com.example.login.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public static BadRequestException validateException(String message) {
        return new BadRequestException(message);
    }

    public static BadRequestException activateNoToken() {
        return new BadRequestException("user.activate.no.token");
    }

    public static BadRequestException activateFail() {
        return new BadRequestException("user.activate.fail");
    }

    public static BadRequestException resendActivationNoEmail() {
        return new BadRequestException("user.activation.no.email");
    }

}
