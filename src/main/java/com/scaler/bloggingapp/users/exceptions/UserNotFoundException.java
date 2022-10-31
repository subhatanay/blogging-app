package com.scaler.bloggingapp.users.exceptions;

public class UserNotFoundException extends Exception {
    private Integer errorCode = 400;

    public UserNotFoundException(String message) {
        super(message);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
