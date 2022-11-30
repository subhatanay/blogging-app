package com.scaler.bloggingapp.comments.exceptions;

import lombok.Getter;

@Getter
public class NoCommentFoundException extends RuntimeException {

    private int errorCode = 400;

    public NoCommentFoundException(String message) {
        super(message);
    }
}
