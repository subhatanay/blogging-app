package com.scaler.bloggingapp.articles.dtos;

import com.scaler.bloggingapp.common.dto.Validation;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticlePutRequestDTO implements Validation {

    private String subject;
    private String content;
    private String description;

    @Override
    public boolean validate() throws ValidationException {
        if (subject!=null && !StringUtils.hasText(subject)) {
            throw new ValidationException("Provide article subject");
        }

        if (description!=null && (!StringUtils.hasText(description) || description.length() > 200)) {
            throw new ValidationException("Provide some description about article within 200 characters");
        }

        if (content!=null && (!StringUtils.hasText(content) || content.length() > 2000) ){
            throw new ValidationException("Provide article content within 2000 characters");
        }

        return true;
    }
}
