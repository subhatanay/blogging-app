package com.scaler.bloggingapp.articles.controllers;

import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.articles.services.ArticleService;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/articles/{articleId}/like")
@SecurityRequirement(name = "authenticatedAPIS")
public class LikesController {
    private ArticleService articleService;

    public LikesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity likeArticleByUser( @PathVariable("articleId") Long articleId) {
        Long userId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        boolean isLikeSuccess = articleService.likeArticle(userId, articleId);
        if (isLikeSuccess) {
           return ResponseEntity.ok().build();
        } else {
           return ResponseEntity.status(HttpStatus.CONFLICT)
                   .body(ErrorResponseDTO.builder()
                           .errorCode(409)
                           .errorMessage("User already liked this article")
                           .build());
        }
    }
    @DeleteMapping
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity disLikeArticleByUser( @PathVariable("articleId") Long articleId) {
        Long userId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        boolean isDisLikeSuccess = articleService.disLikeArticle(userId, articleId);
        if (isDisLikeSuccess) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest()
                    .body(ErrorResponseDTO.builder()
                            .errorCode(400)
                            .errorMessage("User may already disliked this article. Dislike operation not allowed multiple time.")
                            .build());
        }
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbiddenRequestException(ArticleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
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
