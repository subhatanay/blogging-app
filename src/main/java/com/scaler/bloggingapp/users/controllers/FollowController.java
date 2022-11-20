package com.scaler.bloggingapp.users.controllers;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/follow")
public class FollowController {
    private UserService userService;

    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity followUser(@PathVariable("userId") Long followUserId) {
        Long currentUserId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        boolean isFollowSuccess = userService.followUser(currentUserId, followUserId);

        if (currentUserId.equals(followUserId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponseDTO.builder()
                            .errorCode(409)
                            .errorMessage("User can't follow oneself")
                            .build());
        }
        if (isFollowSuccess) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ErrorResponseDTO.builder()
                            .errorCode(409)
                            .errorMessage("You already followed this user.")
                            .build());
        }
    }

    @DeleteMapping
    public ResponseEntity unFollowUser(@PathVariable("userId") Long followUserId) {
        Long currentUserId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        boolean isUnFollowSuccess = userService.unFollowUser(currentUserId, followUserId);

        if (currentUserId.equals(followUserId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponseDTO.builder()
                            .errorCode(409)
                            .errorMessage("User can't unfollow oneself")
                            .build());
        }

        if (isUnFollowSuccess) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponseDTO.builder()
                            .errorCode(400)
                            .errorMessage("Invalid Request.")
                            .build());
        }
    }

}
