package com.scaler.bloggingapp.users.services;


import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dto.*;
import com.scaler.bloggingapp.users.exceptions.InvalidUserCredentialsException;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;

public interface UserService {

    public abstract UserPostResponseDTO createUser(UserPostRequestDTO userPostDto);

    public abstract UserGetResponseDTO updateUserProfile(Long userId,UserPutRequestDto userPutRequestDto);

    public abstract UserGetResponseDTO getUser(Long userId);

    public abstract PagedResults<UserGetResponseDTO> getUsers(Integer pageSize, Integer offset) throws UserNotFoundException;

    public abstract boolean deleteUser(Long userId);

    public abstract UserGetResponseDTO findUserByUsername(String emailId);

    public abstract UserGetResponseDTO loginUser(LoginRequestDTO loginRequestDTO);

    public boolean followUser(Long userId, Long followerUserId);

    public abstract boolean unFollowUser(Long userId, Long followerUserId);

    public abstract PagedResults<UserGetResponseDTO> getUserFollowers(Long userId, Integer offset, Integer pageSize);

    public abstract PagedResults<UserGetResponseDTO> getUserFollowings(Long userId, Integer offset, Integer pageSize);

    public abstract boolean isUserFollowing(Long currentUserId, Long followingUserId);
}
