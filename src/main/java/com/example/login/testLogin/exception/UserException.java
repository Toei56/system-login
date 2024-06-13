package com.example.login.testLogin.exception;

import com.example.login.testLogin.entityModel.User;

public class UserException extends BaseException {
    public UserException(String message) {
        super("user." + message);
    }

    public static UserException createDuplicate() {
        return new UserException("create.email.duplicate");
    }

    public static UserException loginFailEmailNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }

    public static UserException loginFailUserUnactivated() {
        return new UserException("login.fail.unactivated");
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }

    public static UserException activateNoToken() {return new UserException("activate.no.token");}

    public static UserException activateFail() {return new UserException("activate.fail");}

    public static UserException activateAlready() {return new UserException("activate.already");}


    public static UserException activateTokenExpire() {return new UserException("activate.token.expire");}

    public static UserException resendActivationNoEmail() {return new UserException("resend.activation.no.email");}

    public static UserException resendActivationEmailNotFound() {
        return new UserException("resend.activation.fail");
    }

}
