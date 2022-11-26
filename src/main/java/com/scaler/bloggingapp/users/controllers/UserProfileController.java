package com.scaler.bloggingapp.users.controllers;

import com.scaler.bloggingapp.articles.entity.LikedArticleInfo;
import com.scaler.bloggingapp.articles.services.ArticleService;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.common.exceptions.ForbiddenRequestException;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.dto.UserPutRequestDto;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import com.scaler.bloggingapp.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/profile")
public class UserProfileController {
    private UserService userService;
    private  ArticleService articleService;

    public UserProfileController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("/info")
    public ResponseEntity getUserProfile(@PathVariable("userId") Long userId) {
        UserGetResponseDTO userGetResponseDTO = userService.getUser(userId);
        return ResponseEntity.ok().body(userGetResponseDTO);
    }

    @PatchMapping("/info")
    public ResponseEntity updateUserProfile(@PathVariable("userId") Long userId, @RequestBody UserPutRequestDto userPutRequestDto) {
        Long currentUserId =  CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        if (!(currentUserId.equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
            throw new ForbiddenRequestException("Update profile request forbidden request");
        }

        UserGetResponseDTO userGetResponseDTO = userService.updateUserProfile(userId,userPutRequestDto);
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

    @GetMapping("/liked/articles")
    public ResponseEntity getUserLikedPosts(@PathVariable("userId") Long userId,
                                            @RequestParam(value = "offset",defaultValue = "0") Integer offset,
                                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        PagedResults<LikedArticleInfo> feedArticleContentPagedResults = articleService.likedArticlesByUser(userId,limit,offset);
        return ResponseEntity.ok().body(feedArticleContentPagedResults);
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

}
