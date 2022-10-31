package com.scaler.bloggingapp.users.dto;

import com.scaler.bloggingapp.common.exceptions.ValidationException;

public interface Validation {

    public abstract boolean validate() throws ValidationException;
}
