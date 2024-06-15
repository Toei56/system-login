package com.example.login.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public static ConflictException createDuplicate() {
        return new ConflictException("user.create.email.duplicate");
    }

    public static ConflictException activateAlready() {
        return new ConflictException("user.activate.already");
    }
}
