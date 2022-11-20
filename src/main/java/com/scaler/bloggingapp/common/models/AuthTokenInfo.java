package com.scaler.bloggingapp.common.models;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthTokenInfo {
    private Long userId;
    private String userName;
    private List<String> roles;

}
