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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RestController
@RequestMapping("/users/{userId}/articles")
public class ArticleController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity createArticle(@PathVariable("userId") Long userId, @RequestBody ArticlePostRequestDTO articlePostRequestDTO) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        if (!(authTokenInfo.getUserId().equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
            throw new ForbiddenRequestException("Article creation forbidden request");
        }
        ArticlePostResponseDTO articlePostResponseDTO = articleService.createArticle(userId, articlePostRequestDTO);
        return ResponseEntity
                .created(URI.create("/users/" + userId + "/articles/" + articlePostResponseDTO.getArticleId()))
                .body(articlePostResponseDTO);
    }

    @PutMapping("/{articleId}")
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity updateArticle(@PathVariable("userId") Long userId,
                                        @PathVariable("userId") Long articleId,
                                        @RequestBody ArticlePutRequestDTO articlePutRequestDTO) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        if (!(authTokenInfo.getUserId().equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
            throw new ForbiddenRequestException("Article update forbidden request");
        }
        ArticleGetResponseDTO articleGETResponseDTO = articleService.updateArticleByArticleIdAndUserId(userId,articleId, articlePutRequestDTO);
        return ResponseEntity
                .ok()
                .body(articleGETResponseDTO);
    }

    @GetMapping
    @RolesAllowed("ROLE_USER")
    public ResponseEntity getArticlesByUser(@PathVariable("userId") Long userId,
                                            @RequestParam(value = "limit",defaultValue = "10") Integer limit ,
                                            @RequestParam(value="offset",defaultValue = "0") Integer offset) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        if (!(authTokenInfo.getUserId().equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
            throw new ForbiddenRequestException("Articles get by user forbidden request");
        }

        PagedResults<ArticleGetResponseDTO> articlePostResponseDTOPagedResults = articleService.getUserPostedArticles(userId,offset, limit);
        return ResponseEntity.ok(articlePostResponseDTOPagedResults);
    }

    @GetMapping("/{articleId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity getArticleByUserIdAndArticleId(@PathVariable("userId") Long userId,
                                            @PathVariable("articleId") Long articleId) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        if (!(authTokenInfo.getUserId().equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
            throw new ForbiddenRequestException("Articles get by user forbidden request");
        }

        ArticleGetResponseDTO articleGetResponseDTO = articleService.getArticleByArticleIdAndUserId(userId,articleId);
        return ResponseEntity.ok(articleGetResponseDTO);
    }

    @DeleteMapping("/{articleId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity deleteArticleByUserIdAndArticleId(@PathVariable("userId") Long userId,
                                                         @PathVariable("articleId") Long articleId) {
        AuthTokenInfo authTokenInfo = CurrentAuthenticationHolder.getCurrentAuthenticationContext();

        if (!(authTokenInfo.getUserId().equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
            throw new ForbiddenRequestException("Articles delete by user forbidden request");
        }
        articleService.deleteArticleByArticleIdAndUserId(userId,articleId);
        return ResponseEntity.ok().build();
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

    @ExceptionHandler(ForbiddenRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbiddenRequestException(ForbiddenRequestException exception) {
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> parentExceptionHandle(Exception exception) {
        return ResponseEntity.status(HttpStatus.valueOf(500))
                .body(ErrorResponseDTO.builder()
                        .errorCode(500)
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> hadleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }

}
