package com.scaler.bloggingapp.comments.dtos;

import com.scaler.bloggingapp.common.dto.Validation;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentPostRequestDTO implements Validation {
    private Long parentCommentId;

    private String commentData;

    @Override
    public boolean validate() throws ValidationException {
        if (commentData == null || commentData.trim().length() >255) {
            throw new ValidationException("Comments Data must be provided within 255 character limit.");
        }
        return true;
    }
}
