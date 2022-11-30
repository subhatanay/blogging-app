package com.scaler.bloggingapp.users.services;

import com.scaler.bloggingapp.common.models.AuthTokenInfo;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.entity.JwtAuthentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationManager implements AuthenticationManager {
    private JwtService jwtService;
    private UserService userService;


    public JwtAuthenticationManager(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

        String jwtString =  (String) jwtAuthentication.getCredentials();
        String userName = jwtService.findUsernameFromJWT(jwtString);

        UserGetResponseDTO user = userService.findUserByUsername(userName);

        AuthTokenInfo authTokenInfo = AuthTokenInfo.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .roles(user.getRoles())
                .build();

        jwtAuthentication.setUser(authTokenInfo);

        return jwtAuthentication;
    }
}
