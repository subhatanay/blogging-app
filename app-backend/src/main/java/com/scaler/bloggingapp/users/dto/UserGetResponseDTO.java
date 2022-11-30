package com.scaler.bloggingapp.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.users.entity.UserEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserGetResponseDTO implements Serializable {

    private Long userId;
    private String username;
    private String fullName;
    private String emailId;
    private String userLogoUrl;
    private String bio;
    private Date createdDate;
    private List<String> roles;
    private Integer followersCount;
    private Integer followingCount;
    private boolean following;
    private String token;


    private ErrorResponseDTO errorResponse;


    public static UserGetResponseDTO buildFrom(UserEntity userEntity) {
        UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO();

        userGetResponseDTO.setUserId(userEntity.getUserId());
        userGetResponseDTO.setUsername(userEntity.getUsername());
        userGetResponseDTO.setEmailId(userEntity.getEmailId());
        userGetResponseDTO.setUserLogoUrl(userEntity.getProfileImageLink());
        userGetResponseDTO.setFullName(userEntity.getFullName());
        userGetResponseDTO.setCreatedDate(userEntity.getCreateTimestamp());
        userGetResponseDTO.setBio(userEntity.getBio());
        userGetResponseDTO.setUserLogoUrl(userEntity.getProfileImageLink());

        return userGetResponseDTO;

    }


}
