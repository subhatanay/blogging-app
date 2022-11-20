package com.scaler.bloggingapp.comments.exceptions;

import lombok.Getter;

@Getter
public class CommentNotAllowedException extends RuntimeException {

    private int errorCode = 404;

    public CommentNotAllowedException(String message) {
        super(message);
    }
}
