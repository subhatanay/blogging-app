package com.scaler.bloggingapp.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class LoginResponseDTO {
    Long userId;
    String userName;
    String token;

    private ErrorResponseDTO errorResponse;
}
