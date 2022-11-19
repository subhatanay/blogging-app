package com.scaler.bloggingapp.blogs.dtos;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BlogPostResponseDTO {

    private Long blogId;
    private ErrorResponseDTO errorResponse;

}
