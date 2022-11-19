package com.scaler.bloggingapp.users.services;


import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dto.*;
import com.scaler.bloggingapp.users.exceptions.InvalidUserCredentialsException;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;

public interface UserService {

    public abstract UserPostResponseDTO createUser(UserPostRequestDTO userPostDto);

    public abstract UserGetResponseDTO getUser(Long userId);

    public abstract PagedResults<UserGetResponseDTO> getUsers(Integer pageSize, Integer offset) throws UserNotFoundException;

    public abstract boolean deleteUser(Long userId);

    public abstract UserGetResponseDTO findUserByUsername(String emailId);

    public abstract LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO);
}
