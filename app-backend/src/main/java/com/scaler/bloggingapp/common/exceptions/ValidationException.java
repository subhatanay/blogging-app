package com.scaler.bloggingapp.common.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends IllegalArgumentException {
    private Integer errorCode  = 400;

    public ValidationException(Integer errorCode , String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
    public ValidationException( String errorMessage) {
        super(errorMessage);
    }
}
