package com.scaler.bloggingapp.users.exceptions;

public class UserNotFoundException extends RuntimeException {
    private Integer errorCode = 400;

    public UserNotFoundException(String message) {
        super(message);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
