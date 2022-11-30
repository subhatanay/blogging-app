package com.scaler.bloggingapp.users.dto;

import com.scaler.bloggingapp.common.dto.Validation;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPostRequestDTO implements Serializable, Validation {

    private String username;
    private String emailId;
    private String password;


    @Override
    public boolean validate() throws ValidationException {
        if (!StringUtils.hasText(this.username)) {
            throw new ValidationException("Please provide a username");
        }

        if (!StringUtils.hasText(this.emailId)) {
            throw new ValidationException("Please provide user's email id");
        }

        if (!StringUtils.hasText(this.password)) {
            throw new ValidationException("Please provide user's password");
        }
        return true;
    }
}
