package com.scaler.bloggingapp.articles.controllers;

import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.articles.dtos.ArticleGetResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostRequestDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePutRequestDTO;
import com.scaler.bloggingapp.articles.services.ArticleService;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.common.exceptions.ForbiddenRequestException;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import com.scaler.bloggingapp.common.models.AuthTokenInfo;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.controllers.AuthenticationController;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RestController
@RequestMapping("/articles")
@SecurityRequirement(name = "Credentials")

public class ArticleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity createArticle( @RequestBody ArticlePostRequestDTO articlePostRequestDTO) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        ArticlePostResponseDTO articlePostResponseDTO = articleService.createArticle(authTokenInfo.getUserId(), articlePostRequestDTO);
        return ResponseEntity
                .created(URI.create("/users/" + authTokenInfo.getUserId() + "/articles/" + articlePostResponseDTO.getArticleId()))
                .body(articlePostResponseDTO);
    }

    @PutMapping("/{articleId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity updateArticle(
                                        @PathVariable("articleId") Long articleId,
                                        @RequestBody ArticlePutRequestDTO articlePutRequestDTO) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        ArticleGetResponseDTO articleGETResponseDTO = articleService.updateArticleByArticleIdAndUserId(authTokenInfo.getUserId(),articleId, articlePutRequestDTO);
        return ResponseEntity
                .ok()
                .body(articleGETResponseDTO);
    }

    @DeleteMapping("/{articleId}")
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity deleteArticleByArticleId(@PathVariable("articleId") Long articleId) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();
        if (CurrentAuthenticationHolder.isSysAdmin()) {
            articleService.deleteArticleByArticleId(articleId);
        } else {
            articleService.deleteArticleByArticleIdAndUserId(authTokenInfo.getUserId(),articleId);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/{articleId}")
    public ResponseEntity getArticleById(@PathVariable("articleId") Long articleId) {
        boolean isAuth = CurrentAuthenticationHolder.isAuthenticatedRequest();
        ArticleGetResponseDTO articleGetResponseDTO = isAuth ?  articleService.getArticleByArticleIdAndUserId(CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId(),articleId)
                                                                : articleService.getArticle(articleId);
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

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleArgumentValidationException(ValidationException validationException) {
        return ResponseEntity.status(HttpStatus.valueOf(validationException.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(validationException.getErrorCode())
                        .errorMessage(validationException.getMessage())
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
