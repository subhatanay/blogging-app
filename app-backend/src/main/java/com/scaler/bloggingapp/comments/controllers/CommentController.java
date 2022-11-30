package com.scaler.bloggingapp.comments.controllers;

import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.comments.dtos.CommentGetResponseDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPostRequestDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPostResponseDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPutRequestDTO;
import com.scaler.bloggingapp.comments.exceptions.CommentNotAllowedException;
import com.scaler.bloggingapp.comments.exceptions.NoCommentFoundException;
import com.scaler.bloggingapp.comments.services.CommentsService;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.common.exceptions.ForbiddenRequestException;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.text.MessageFormat;

@RestController
@RequestMapping("/articles/{articleId}/comments")
@SecurityRequirement(name = "authenticatedAPIS")
public class CommentController {
    private final CommentsService commentsService;

    public CommentController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @PostMapping
    @RolesAllowed({"ROLE_SYSADMIN","ROLE_USER"})
    public ResponseEntity createComment(@PathVariable("articleId") Long articleId, @RequestBody CommentPostRequestDTO commentRequestBody) {
        Long currentUserId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        CommentPostResponseDTO commentPostResponseDTO = this.commentsService.createComment(currentUserId, articleId,commentRequestBody);

        return ResponseEntity.created(URI.create(MessageFormat.format("/articles/{0}/comments/{1}",articleId, commentPostResponseDTO.getCommentId())))
                .body(commentPostResponseDTO);
    }

    @GetMapping
    public ResponseEntity getCommentsByArticleId(@PathVariable("articleId") Long articleId,
                                                 @RequestParam(value = "offset",defaultValue = "0") Integer offset,
                                                 @RequestParam(value = "limit",defaultValue  = "100") Integer limit) {
        PagedResults<CommentGetResponseDTO>  commentGetResponseDTOPagedResults = commentsService.getCommentsByArticle(articleId,offset, limit);
        return ResponseEntity.ok().body(commentGetResponseDTOPagedResults);
    }

    @PutMapping("/{commentId}")
    @RolesAllowed({"ROLE_SYSADMIN","ROLE_USER"})
    public ResponseEntity updateCommentByArticleId(@PathVariable("articleId") Long articleId,
                                                   @PathVariable("commentId") Long commentId,
                                                   @RequestBody CommentPutRequestDTO commentRequestBody) {
        CommentGetResponseDTO commentGetResponseDTO = commentsService.updateCommentByCommentIdAndArticleId(articleId,commentId, commentRequestBody);
        return ResponseEntity.ok().body(commentGetResponseDTO);
    }

    @DeleteMapping("/{commentId}")
    @RolesAllowed({"ROLE_SYSADMIN","ROLE_USER"})
    public ResponseEntity deleteComment(@PathVariable("articleId") Long articleId,
                                        @PathVariable("commentId") Long commentId){
        Long currentUserId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        if (!CurrentAuthenticationHolder.isSysAdmin() && !commentsService.isEligibleToDeleteComment(currentUserId,commentId)) {
            throw new ForbiddenRequestException("Comment remove forbidden request");
        }

        commentsService.deleteComment(articleId, commentId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(NoCommentFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCommentNotException(NoCommentFoundException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(CommentNotAllowedException.class)
    public ResponseEntity<ErrorResponseDTO> handleCommentNotAllowedException(CommentNotAllowedException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getErrorCode()))
                .body(ErrorResponseDTO.builder()
                        .errorCode(exception.getErrorCode())
                        .errorMessage(exception.getMessage())
                        .build()
                );
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
