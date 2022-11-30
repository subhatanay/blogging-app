package com.scaler.bloggingapp.common.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponseDTO {

    private Integer errorCode;
    private String errorMessage;

}
