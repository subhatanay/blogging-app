package com.scaler.bloggingapp.common.exceptions;

import lombok.Getter;

@Getter
public class ForbiddenRequestException extends  RuntimeException {
    private Integer errorCode  = 403;

    public ForbiddenRequestException(String message) {
        super(message);
    }
}
