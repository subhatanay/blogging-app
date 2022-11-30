package com.scaler.bloggingapp.users.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {

    private Integer errorCode = 401;

    public InvalidUserCredentialsException(String message) {
        super(message);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
