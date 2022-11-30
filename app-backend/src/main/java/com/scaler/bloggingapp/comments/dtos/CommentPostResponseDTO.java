package com.scaler.bloggingapp.comments.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentPostResponseDTO {

    private Long  commentId;
    private ErrorResponseDTO errorResponse;

}
