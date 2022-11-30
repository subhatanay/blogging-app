package com.scaler.bloggingapp.users.entity;

import com.scaler.bloggingapp.common.models.AuthTokenInfo;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthentication implements Authentication {

    private String jwtString;
    private AuthTokenInfo authTokenInfo;


    public JwtAuthentication(String jwtString) {
        this.jwtString = jwtString;
    }

    public void setUser(AuthTokenInfo authTokenInfo) {
        this.authTokenInfo= authTokenInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authTokenInfo != null) {
            List<SimpleGrantedAuthority> roleList = new ArrayList<>();
            for (String role : authTokenInfo.getRoles()) {
                roleList.add(new SimpleGrantedAuthority(role));
            }
            return roleList;
        }
        return null;
    }

    @Override
    public Object getCredentials() {
        return jwtString;
    }

    @Override
    public Object getDetails() {
       return authTokenInfo;
    }

    @Override
    public Object getPrincipal() {
        return this.authTokenInfo;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authTokenInfo != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.authTokenInfo.getUserName();
    }
}
