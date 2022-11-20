package com.scaler.bloggingapp.users.controllers;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import com.scaler.bloggingapp.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile")
public class UserProfileController {
    private UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity getUserProfile(@PathVariable("userId") Long userId) {
        UserGetResponseDTO userGetResponseDTO = userService.getUser(userId);
        return ResponseEntity.ok().body(userGetResponseDTO);
    }

    @GetMapping("/followers")
    public ResponseEntity getUserFollowers(@PathVariable("userId") Long userId,
                                           @RequestParam(value = "offset",defaultValue = "0") Integer offset,
                                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        PagedResults<UserGetResponseDTO> userGetResponseDTOPagedResults = userService.getUserFollowers(userId,offset,limit);
        return ResponseEntity.ok().body(userGetResponseDTOPagedResults);
    }

    @GetMapping("/followings")
    public ResponseEntity getUserFollowings(@PathVariable("userId") Long userId,
                                           @RequestParam(value = "offset",defaultValue = "0") Integer offset,
                                           @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        PagedResults<UserGetResponseDTO> userGetResponseDTOPagedResults = userService.getUserFollowings(userId,offset,limit);
        return ResponseEntity.ok().body(userGetResponseDTOPagedResults);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> parentExceptionHandle(Exception exception) {
        return ResponseEntity.status(HttpStatus.valueOf(500))
                .body(ErrorResponseDTO.builder()
                        .errorCode(500)
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }


}
