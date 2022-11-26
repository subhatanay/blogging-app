package com.scaler.bloggingapp.users.controllers;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import com.scaler.bloggingapp.users.dto.LoginRequestDTO;
import com.scaler.bloggingapp.users.dto.LoginResponseDTO;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
import com.scaler.bloggingapp.users.dto.UserPostResponseDTO;
import com.scaler.bloggingapp.users.exceptions.InvalidUserCredentialsException;
import com.scaler.bloggingapp.users.exceptions.UserAlreadyExistsException;
import com.scaler.bloggingapp.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signUpUser(@RequestBody UserPostRequestDTO createUserDto) {
        UserPostResponseDTO userPostResponseDTO = userService.createUser(createUserDto);
        if (userPostResponseDTO.getUserId()!=null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED).body(userPostResponseDTO);
        } else {
            return ResponseEntity
                    .status(HttpStatus.valueOf(userPostResponseDTO.getErrorResponse().getErrorCode()))
                    .body(userPostResponseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
       LoginResponseDTO loginResponseDTO = userService.loginUser(loginRequestDTO);
       return ResponseEntity.ok().body(loginResponseDTO);
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyException(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }

    // Exception Handlers
    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserInvalidCredsException(InvalidUserCredentialsException invalidUserCredentialsException) {
        return ResponseEntity.status(HttpStatus.valueOf(invalidUserCredentialsException.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(invalidUserCredentialsException.getErrorCode())
                        .errorMessage(invalidUserCredentialsException.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleArgumentValidationException(ValidationException validationException) {
        return ResponseEntity.status(HttpStatus.valueOf(validationException.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(validationException.getErrorCode())
                        .errorMessage(validationException.getMessage())
                        .build()
                );
    }


}
