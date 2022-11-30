package com.scaler.bloggingapp.common.dto;

import com.scaler.bloggingapp.common.exceptions.ValidationException;

public interface Validation {

    public abstract boolean validate() throws ValidationException;
}
