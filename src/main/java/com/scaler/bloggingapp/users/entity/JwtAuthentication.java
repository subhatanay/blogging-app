package com.scaler.bloggingapp.users.entity;

import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JwtAuthentication implements Authentication {

    private String jwtString;
    private UserGetResponseDTO userGetResponseDTO;


    public JwtAuthentication(String jwtString) {
        this.jwtString = jwtString;
    }

    public void setUser(UserGetResponseDTO userGetResponseDTO) {
        this.userGetResponseDTO= userGetResponseDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userGetResponseDTO != null) {
            List<SimpleGrantedAuthority> roleList = new ArrayList<>();
            for (String role : userGetResponseDTO.getRoles()) {
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
       return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userGetResponseDTO;
    }

    @Override
    public boolean isAuthenticated() {
        return this.userGetResponseDTO != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.userGetResponseDTO.getUsername();
    }
}
