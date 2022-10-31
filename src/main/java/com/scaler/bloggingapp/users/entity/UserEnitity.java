package com.scaler.bloggingapp.users.entity;

import com.scaler.bloggingapp.blogs.entity.BlogEntity;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
import com.scaler.bloggingapp.comments.entity.CommentsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class UserEnitity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    @Column(name = "email_id", unique = true)
    private String emailId;
    private String password;
    private String userLogoUrl;
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<UserEnitity> followers;

    @OneToMany
    private Set<BlogEntity> publishedArticles;

    @OneToMany
    private Set<BlogEntity> likedArticles;

    @OneToMany(mappedBy = "user")
    private Set<CommentsEntity> userComments;

    public static UserEnitity buildFromUserDTO(UserPostRequestDTO userData) {
        UserEnitity userEnitity = new UserEnitity();
        userEnitity.setName(userData.getName());
        userEnitity.setEmailId(userData.getEmailId());
        userEnitity.setUserLogoUrl(userData.getUserLogoUrl());
        userEnitity.setPassword(userData.getPassword());
        userEnitity.setCreatedAt(new Date());

        return userEnitity;
    }

}
