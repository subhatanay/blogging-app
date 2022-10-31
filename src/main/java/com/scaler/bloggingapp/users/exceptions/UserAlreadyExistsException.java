package com.scaler.bloggingapp.users.exceptions;

public class UserAlreadyExistsException  extends  Exception {
    private int errorCode = 409;

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
