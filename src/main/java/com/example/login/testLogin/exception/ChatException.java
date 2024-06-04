package com.example.login.testLogin.exception;

public class ChatException extends BaseException {
    public ChatException(String message) {
        super("char." + message);
    }

    public static ChatException accessDenied() {
        return new ChatException("access.denied");
    }
}
