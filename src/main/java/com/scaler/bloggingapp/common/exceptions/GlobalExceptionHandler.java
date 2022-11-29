package com.scaler.bloggingapp.common.exceptions;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ForbiddenRequestException.class})
    public ResponseEntity<ErrorResponseDTO> handleForbiddenRequestException(ForbiddenRequestException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> parentExceptionHandle(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.valueOf(500))
                .body(ErrorResponseDTO.builder()
                        .errorCode(500)
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }

}
