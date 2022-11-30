package com.scaler.bloggingapp.articles.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticlePostResponseDTO {

    private Long articleId;
    private ErrorResponseDTO errorResponse;

}
