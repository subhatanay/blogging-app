package com.scaler.bloggingapp.users.services;


import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dao.RoleRepository;
import com.scaler.bloggingapp.users.dao.UserRepository;
import com.scaler.bloggingapp.users.dto.*;
import com.scaler.bloggingapp.users.entity.RolesEntity;
import com.scaler.bloggingapp.users.entity.UserEntity;
import com.scaler.bloggingapp.users.exceptions.InvalidUserCredentialsException;
import com.scaler.bloggingapp.users.exceptions.UserAlreadyExistsException;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private   BCryptPasswordEncoder passwordEncoder;

    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UserPostResponseDTO createUser(UserPostRequestDTO userPostDto) {
            userPostDto.validate();
            if(userRepository.existsByEmailId(userPostDto.getEmailId())) {
                throw new UserAlreadyExistsException("User's Email Id already exists.");
            }
            if(userRepository.existsByUsername(userPostDto.getUsername())) {
                throw new UserAlreadyExistsException("Username is already exists. Choose a different one");
            }

            UserEntity userEntity = UserEntity.buildFromUserDTO(userPostDto);
            userEntity.setPassword(passwordEncoder.encode(userPostDto.getPassword()));

            Optional<RolesEntity> rolesUserEntity = roleRepository.findByRoleName("ROLE_USER");

            if (rolesUserEntity.isPresent()) {
                userEntity.getUserRoles().add(rolesUserEntity.get());
            }

            userEntity = userRepository.save(userEntity);
            return UserPostResponseDTO
                    .builder()
                    .userId(userEntity.getUserId())
                    .token(jwtService.createJwt(userEntity.getUsername(), userEntity.getRoles()))
                    .build();

    }

    @Override
    public UserGetResponseDTO getUser(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userId));
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException(MessageFormat.format("User with id {0} not exists", userId));
        }

        return UserGetResponseDTO.buildFrom(userEntity.get());
    }

    @Override
    public PagedResults<UserGetResponseDTO> getUsers(Integer pageSize, Integer offset) throws UserNotFoundException {
        Page<UserEntity> userEntityList = userRepository.findAll(Pageable.ofSize(pageSize).withPage(offset));

        if (userEntityList == null || userEntityList.getTotalElements()==0) {
            throw new UserNotFoundException("No Users found");
        }

        List<UserGetResponseDTO> users = userEntityList.get().map(user -> {
            UserGetResponseDTO userDTO = UserGetResponseDTO.buildFrom(user);
            userDTO.setFollowersCount(user.getFollowers().size());
            userDTO.setFollowingCount(user.getFollowings().size());

            return userDTO;
        }).collect(Collectors.toList());
        return PagedResults.<UserGetResponseDTO>builder()
                .totalCount((int) userEntityList.getTotalElements())
                .pageCount(userEntityList.getTotalPages())
                .pageSize(users.size())
                .results(users)
                .build();
    }

    @Override
    public boolean deleteUser(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity == null || !userEntity.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(userEntity.get());
        return true;
    }

    @Override
    public UserGetResponseDTO findUserByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        if (userEntity == null || !userEntity.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        UserGetResponseDTO userDTO  = UserGetResponseDTO.buildFrom(userEntity.get());
        userDTO.setRoles(userEntity.get().getRoles());

        return userDTO;
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        loginRequestDTO.validate();
        Optional<UserEntity> userEntity = userRepository.findByEmailId(loginRequestDTO.getEmailId());
        if (userEntity == null || !userEntity.isPresent()) {
            throw new InvalidUserCredentialsException("User not exists or Bad credentials provided.");
        }
        UserEntity user = userEntity.get();
        if (passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())) {
           return  LoginResponseDTO.builder()
                    .userName(user.getUsername())
                    .userId(user.getUserId())
                    .token(jwtService.createJwt(user.getUsername(),user.getRoles()))
                   .build();
        }
        throw new InvalidUserCredentialsException("User not exists or Bad credentials provided.");
    }

    @Override
    public boolean followUser(Long userId, Long followerUserId) {
        return followUnfollowUser(userId,followerUserId,true);
    }
    @Override
    public boolean unFollowUser(Long currentUserId, Long followerUserId) {
        return followUnfollowUser(currentUserId,followerUserId,false);
    }

    @Override
    public PagedResults<UserGetResponseDTO> getUserFollowers(Long userId, Integer offset, Integer pageSize) {
        UserEntity user = findByUserId(userId);

        Page<UserEntity> userFollowersPagedList = userRepository.findFollowersByUserId(userId,Pageable.ofSize(pageSize).withPage(offset));

        if (userFollowersPagedList == null || userFollowersPagedList.getTotalElements() == 0) {
            throw new UserNotFoundException("No User follows this user");
        }

        List<UserGetResponseDTO> followersList = userFollowersPagedList.stream().map(userD -> UserGetResponseDTO.buildFrom(userD)).collect(Collectors.toList());
        return PagedResults.<UserGetResponseDTO>builder()
                .totalCount((int) userFollowersPagedList.getTotalElements())
                .pageSize(followersList.size())
                .results(followersList)
                .build();
    }
    @Override
    public PagedResults<UserGetResponseDTO> getUserFollowings(Long userId, Integer offset, Integer pageSize) {
        Page<UserEntity> userFollowingsPagedList = userRepository.findFollowingsByUserId(userId,Pageable.ofSize(pageSize).withPage(offset));

        if (userFollowingsPagedList == null || userFollowingsPagedList.getTotalElements() == 0) {
            throw new UserNotFoundException("User does not follow anyone");
        }

        List<UserGetResponseDTO> followingsList = userFollowingsPagedList.stream().map(userD -> UserGetResponseDTO.buildFrom(userD)).collect(Collectors.toList());
        return PagedResults.<UserGetResponseDTO>builder()
                .totalCount((int) userFollowingsPagedList.getTotalElements())
                .pageSize(followingsList.size())
                .results(followingsList)
                .build();
    }

    private UserEntity findByUserId(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(Long.valueOf(userId));
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException(MessageFormat.format("User with id {0} not exists", userId));
        }
        return userEntity.get();
    }

    private boolean followUnfollowUser(Long currentUserId, Long followerUserId, boolean followUnfollow) {
        UserEntity currentUser  = findByUserId(currentUserId);

        UserEntity followUser = findByUserId(followerUserId);


        if (followUnfollow) {
            if (!(currentUser.getFollowings().contains(followUser))) {
                currentUser.getFollowings().add(followUser);
                followUser.getFollowers().add(currentUser);

                userRepository.save(currentUser);
                userRepository.save(followUser);
                return true;
            }
        } else {
            if ((currentUser.getFollowings().contains(followUser))) {
                currentUser.getFollowings().remove(followUser);
                followUser.getFollowers().remove(currentUser);

                userRepository.save(currentUser);
                userRepository.save(followUser);
                return true;
            }
        }

        return false;
    }
}
