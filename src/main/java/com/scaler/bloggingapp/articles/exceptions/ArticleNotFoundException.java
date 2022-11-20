package com.scaler.bloggingapp.articles.exceptions;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends RuntimeException {
    private Integer errorCode = 400;

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
