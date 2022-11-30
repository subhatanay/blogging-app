package com.scaler.bloggingapp.common.models;

import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.entity.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentAuthenticationHolder {

    public static AuthTokenInfo getCurrentAuthenticationContext() {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        AuthTokenInfo authTokenInfo = (AuthTokenInfo) jwtAuthentication.getDetails();

        return authTokenInfo;
    }

    public static boolean isAuthenticatedRequest() {
        return SecurityContextHolder.getContext().getAuthentication() == null || (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthentication) ;
    }

    public static boolean isSysAdmin() {
        AuthTokenInfo authTokenInfo = getCurrentAuthenticationContext();
        return authTokenInfo.getRoles().stream().filter(role -> role.equals("ROLE_SYSADMIN")).count() > 0;
    }
}
