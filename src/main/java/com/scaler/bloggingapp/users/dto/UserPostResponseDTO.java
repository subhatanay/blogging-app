package com.scaler.bloggingapp.users.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPostResponseDTO {

    private Long userId;
    private String resourceUrl;

    private ErrorResponseDTO errorResponse;
}
