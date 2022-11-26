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
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPutRequestDto implements Validation {
    private String fullName;
    private String profileImageLink;

    private String password;

    @Override
    public boolean validate() throws ValidationException {
        if (fullName!=null && !StringUtils.hasText(fullName)) {
            throw new ValidationException("Full name should not be blank or empty");
        }
        if (profileImageLink!=null && !StringUtils.hasText(profileImageLink)) {
            throw new ValidationException("Profile Image Link should not be blank or empty");
        }
        if (password!=null && !StringUtils.hasText(password)) {
            throw new ValidationException("Password should not be blank or empty");
        }

        return true;
    }
}
