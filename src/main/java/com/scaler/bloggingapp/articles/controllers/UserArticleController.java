package com.scaler.bloggingapp.articles.controllers;

import com.scaler.bloggingapp.articles.dtos.ArticleGetResponseDTO;
import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.articles.services.ArticleService;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.common.models.AuthTokenInfo;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/users/{userId}/articles")
public class UserArticleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserArticleController.class);
    private ArticleService articleService;

    public UserArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity getArticlesPostedByUser(@PathVariable("userId") Long userId,
                                            @RequestParam(value = "limit",defaultValue = "10") Integer limit ,
                                            @RequestParam(value="offset",defaultValue = "0") Integer offset) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        PagedResults<ArticleGetResponseDTO> articlePostResponseDTOPagedResults = articleService.getUserPostedArticles(userId,offset, limit);
        return ResponseEntity.ok(articlePostResponseDTOPagedResults);
    }

    @GetMapping("/{articleId}")
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity getArticleByUserIdAndArticleId(@PathVariable("userId") Long userId,
                                                         @PathVariable("articleId") Long articleId) {
        ArticleGetResponseDTO articleGetResponseDTO = articleService.getArticleByArticleIdAndUserId(userId,articleId);
        return ResponseEntity.ok(articleGetResponseDTO);
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
