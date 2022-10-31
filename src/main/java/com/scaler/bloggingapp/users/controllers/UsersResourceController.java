package com.scaler.bloggingapp.users.controllers;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
import com.scaler.bloggingapp.users.dto.UserPostResponseDTO;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import com.scaler.bloggingapp.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersResourceController {

    private UserService userService;

    public UsersResourceController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserPostRequestDTO createUserDto) {
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

    @GetMapping(path = "/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Integer userId) {
        UserGetResponseDTO userGetResponseDTO = userService.getUser(userId);

        if (userGetResponseDTO.getErrorResponse()!=null) {
            return ResponseEntity
                    .status(HttpStatus.valueOf(userGetResponseDTO.getErrorResponse().getErrorCode()))
                    .body(userGetResponseDTO);
        }

        return ResponseEntity.ok(userGetResponseDTO);
    }

    @GetMapping
    public ResponseEntity getUsers(@RequestParam(value = "limit",defaultValue = "10") Integer limit , @RequestParam(value="offset",defaultValue = "0") Integer offset) {
        try {
            PagedResults<UserGetResponseDTO> userGetResponseDTOPagedResults = userService.getUsers(limit, offset);
            return ResponseEntity.ok(userGetResponseDTOPagedResults);
        } catch (UserNotFoundException notFoundException) {
            return  ResponseEntity.status(HttpStatus.valueOf(notFoundException.getErrorCode()))
                    .body(UserGetResponseDTO.builder()
                        .errorResponse(ErrorResponseDTO.builder()
                            .errorCode(notFoundException.getErrorCode())
                            .errorMessage(notFoundException.getMessage())
                            .build())
                        .build());
        }




    }
 }
