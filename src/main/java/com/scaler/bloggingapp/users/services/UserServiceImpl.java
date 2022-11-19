package com.scaler.bloggingapp.users.services;


import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dao.UserRepository;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
import com.scaler.bloggingapp.users.dto.UserPostResponseDTO;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.exceptions.ValidationException;
import com.scaler.bloggingapp.users.entity.UserEnitity;
import com.scaler.bloggingapp.users.exceptions.UserAlreadyExistsException;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserPostResponseDTO createUser(UserPostRequestDTO userPostDto) {
        try {
            userPostDto.validate();
            UserEnitity userEnitity = UserEnitity.buildFromUserDTO(userPostDto);

            if(userRepository.existsByEmailId(userPostDto.getEmailId())) {
                throw new UserAlreadyExistsException("User's Email Id already exists.");
            }

            userEnitity = userRepository.save(userEnitity);
            return UserPostResponseDTO
                    .builder()
                    .userId(userEnitity.getUserId())
                    .build();
        } catch (Exception exception) {
            return buildCreateUserException(exception);
        }
    }

    @Override
    public UserGetResponseDTO getUser(Long userId) {
        try {
            Optional<UserEnitity> userEnitity = userRepository.findById(Long.valueOf(userId));
            if (!userEnitity.isPresent()) {
                throw new UserNotFoundException(MessageFormat.format("User with id %s not exists", userId));
            }

            return UserGetResponseDTO.buildFrom(userEnitity.get());
        } catch (UserNotFoundException notFoundException) {
            return UserGetResponseDTO.builder()
                    .errorResponse(ErrorResponseDTO
                            .builder()
                            .errorCode(notFoundException.getErrorCode())
                            .errorMessage(notFoundException.getMessage())
                            .build())
                    .build();
        }

    }

    @Override
    public PagedResults<UserGetResponseDTO> getUsers(Integer pageSize, Integer offset) throws UserNotFoundException {
        Page<UserEnitity> userEntityList = userRepository.findAll(Pageable.ofSize(pageSize).withPage(offset));

        if (userEntityList == null || userEntityList.getTotalElements()==0) {
            throw new UserNotFoundException("No Users found");
        }

        List<UserGetResponseDTO> users = userEntityList.get().map(user -> UserGetResponseDTO.buildFrom(user)).collect(Collectors.toList());
        return PagedResults.<UserGetResponseDTO>builder()
                .totalCount((int) userEntityList.getTotalElements())
                .pageCount(userEntityList.getTotalPages())
                .pageSize(users.size())
                .results(users)
                .build();
    }

    private UserPostResponseDTO buildCreateUserException(Exception exception) {
        UserPostResponseDTO.UserPostResponseDTOBuilder builder = UserPostResponseDTO.builder();

        if (exception instanceof ValidationException) {
            ValidationException validationError = (ValidationException) exception;
            return builder.errorResponse(ErrorResponseDTO
                            .builder()
                            .errorCode(validationError.getErrorCode())
                            .errorMessage(validationError.getMessage()).build())
                    .build();
        } else if (exception instanceof UserAlreadyExistsException) {
            UserAlreadyExistsException excep = (UserAlreadyExistsException) exception;
            return builder.errorResponse(ErrorResponseDTO
                            .builder()
                            .errorCode(excep.getErrorCode())
                            .errorMessage(exception.getMessage()).build())
                    .build();
        } else  {
            return builder.errorResponse(ErrorResponseDTO
                    .builder()
                    .errorCode(500)
                    .errorMessage(exception.getMessage())
                    .build())
                    .build();
        }
    }

}
