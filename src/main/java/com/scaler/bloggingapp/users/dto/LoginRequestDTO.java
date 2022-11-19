package com.scaler.bloggingapp.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.Validation;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import lombok.*;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class LoginRequestDTO implements Validation {
    private String emailId;
    protected String password;

    @Override
    public boolean validate() throws ValidationException {
        if (!StringUtils.hasText(this.emailId)) {
            throw new ValidationException("Please provide user's email id");
        }

        if (!StringUtils.hasText(this.password)) {
            throw new ValidationException("Please provide user's password");
        }

        return true;
    }
}
