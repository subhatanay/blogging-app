package com.scaler.bloggingapp.blogs.dtos;

import com.scaler.bloggingapp.common.exceptions.ValidationException;
import com.scaler.bloggingapp.common.dto.Validation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogPostRequestDTO implements Validation {

    private String subject;
    private String content;

    @Override
    public boolean validate() throws ValidationException {
        if (!StringUtils.hasText(subject)) {
            throw new ValidationException("Please provide Blog Subject");
        }

        if (!StringUtils.hasText(content)) {
            throw new ValidationException("Please provide Blog content");
        }

        return true;
    }
}
