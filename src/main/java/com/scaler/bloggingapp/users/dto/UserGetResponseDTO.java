package com.scaler.bloggingapp.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.users.entity.UserEnitity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserGetResponseDTO implements Serializable {

    private Long userId;
    private String name;
    private String emailId;
    private String userLogoUrl;
    private Date createdDate;

    private ErrorResponseDTO errorResponse;


    public static UserGetResponseDTO buildFrom(UserEnitity userEnitity) {
        UserGetResponseDTO userGetResponseDTO = new UserGetResponseDTO();

        userGetResponseDTO.setUserId(userEnitity.getUserId());
        userGetResponseDTO.setName(userEnitity.getName());
        userGetResponseDTO.setEmailId(userEnitity.getEmailId());
        userGetResponseDTO.setUserLogoUrl(userEnitity.getProfileImageLink());
        userGetResponseDTO.setCreatedDate(userEnitity.getCreateTimestamp());

        return userGetResponseDTO;

    }


}
