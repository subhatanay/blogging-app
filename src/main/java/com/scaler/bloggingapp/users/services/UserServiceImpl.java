package com.scaler.bloggingapp.users.services;


import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dao.RoleRepository;
import com.scaler.bloggingapp.users.dao.UserRepository;
import com.scaler.bloggingapp.users.dto.*;
import com.scaler.bloggingapp.users.entity.RolesEntity;
import com.scaler.bloggingapp.users.entity.UserEnitity;
import com.scaler.bloggingapp.users.exceptions.InvalidUserCredentialsException;
import com.scaler.bloggingapp.users.exceptions.UserAlreadyExistsException;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

            UserEnitity userEnitity = UserEnitity.buildFromUserDTO(userPostDto);
            userEnitity.setPassword(passwordEncoder.encode(userPostDto.getPassword()));

            Optional<RolesEntity> rolesUserEntity = roleRepository.findByRoleName("ROLE_USER");

            if (rolesUserEntity.isPresent()) {
                userEnitity.getUserRoles().add(rolesUserEntity.get());
            }

            userEnitity = userRepository.save(userEnitity);
            return UserPostResponseDTO
                    .builder()
                    .userId(userEnitity.getUserId())
                    .token(jwtService.createJwt(userEnitity.getUsername(),userEnitity.getRoles()))
                    .build();

    }

    @Override
    public UserGetResponseDTO getUser(Long userId) {
        Optional<UserEnitity> userEntity = userRepository.findById(Long.valueOf(userId));
        if (!userEntity.isPresent()) {
            throw new UserNotFoundException(MessageFormat.format("User with id {0} not exists", userId));
        }

        return UserGetResponseDTO.buildFrom(userEntity.get());
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

    @Override
    public boolean deleteUser(Long userId) {
        Optional<UserEnitity> userEntity = userRepository.findById(userId);

        if (userEntity == null || !userEntity.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(userEntity.get());
        return true;
    }

    @Override
    public UserGetResponseDTO findUserByUsername(String username) {
        Optional<UserEnitity> userEntity = userRepository.findByUsername(username);

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
        Optional<UserEnitity> userEntity = userRepository.findByEmailId(loginRequestDTO.getEmailId());
        LOGGER.info("user -> " + userEntity.isPresent());
        if (userEntity == null || !userEntity.isPresent()) {
            throw new InvalidUserCredentialsException("User not exists or Bad credentials provided.");
        }
        UserEnitity user = userEntity.get();
        if (passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())) {
           return  LoginResponseDTO.builder()
                    .userName(user.getUsername())
                    .userId(user.getUserId())
                    .token(jwtService.createJwt(user.getUsername(),user.getRoles()))
                   .build();
        }
        throw new InvalidUserCredentialsException("User not exists or Bad credentials provided.");
    }
}
