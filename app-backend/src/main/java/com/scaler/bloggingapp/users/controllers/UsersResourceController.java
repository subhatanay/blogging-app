package com.scaler.bloggingapp.users.controllers;

import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.common.exceptions.ForbiddenRequestException;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
import com.scaler.bloggingapp.users.dto.UserPostResponseDTO;
import com.scaler.bloggingapp.users.entity.JwtAuthentication;
import com.scaler.bloggingapp.users.exceptions.UserAlreadyExistsException;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import com.scaler.bloggingapp.users.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "authenticatedAPIS")
public class UsersResourceController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersResourceController.class);
    private UserService userService;

    public UsersResourceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity getUser(@PathVariable("userId") Long userId) {
        UserGetResponseDTO userGetResponseDTO = userService.getUser(userId);

        JwtAuthentication authentication = (JwtAuthentication)SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("User logged with token :: " + authentication.getName());

        if (userGetResponseDTO.getErrorResponse()!=null) {
            return ResponseEntity
                    .status(HttpStatus.valueOf(userGetResponseDTO.getErrorResponse().getErrorCode()))
                    .body(userGetResponseDTO);
        }
        return ResponseEntity.ok(userGetResponseDTO);
    }
    @GetMapping(path = "/me")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity getCurrentUser() {
        Long userId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        UserGetResponseDTO userGetResponseDTO = userService.getUser(userId);

        return ResponseEntity.ok(userGetResponseDTO);
    }

    @GetMapping
    @RolesAllowed("ROLE_SYSADMIN")
    public ResponseEntity getUsers(@RequestParam(value = "limit",defaultValue = "10") Integer limit , @RequestParam(value="offset",defaultValue = "0") Integer offset) {
        PagedResults<UserGetResponseDTO> userGetResponseDTOPagedResults = userService.getUsers(limit, offset);
        return ResponseEntity.ok(userGetResponseDTOPagedResults);
    }

    @DeleteMapping("/{userId}")
    @RolesAllowed({"ROLE_USER","ROLE_SYSADMIN"})
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId) {
        try {
            Long currentUserId =  CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
            if (!(currentUserId.equals(userId) || CurrentAuthenticationHolder.isSysAdmin())) {
                throw new ForbiddenRequestException("Delete user request forbidden request");
            }
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException notFoundException) {
            return ResponseEntity.status(HttpStatus.valueOf(notFoundException.getErrorCode()))
                    .body(UserGetResponseDTO.builder()
                            .errorResponse(ErrorResponseDTO.builder()
                                    .errorCode(notFoundException.getErrorCode())
                                    .errorMessage(notFoundException.getMessage())
                                    .build())
                            .build());
        }
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
