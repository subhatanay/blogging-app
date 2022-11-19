package com.scaler.bloggingapp.users.entity;

import com.scaler.bloggingapp.blogs.entity.ArticleEntity;
import com.scaler.bloggingapp.common.models.AuditEntity;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
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
public class UserEnitity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String bio;
    @Column(name = "email_id", unique = true)
    private String emailId;
    private String password;
    private String profileImageLink;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="follow",
                joinColumns = @JoinColumn(name="followed_id"),
                inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<UserEnitity> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<UserEnitity> followings;

    @OneToMany
    @JoinTable(name= "publishedArticles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="article_id"))
    private Set<ArticleEntity> publishedArticles;

    @OneToMany
    @JoinTable(name="likes",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private Set<ArticleEntity> likedArticles;


    public static UserEnitity buildFromUserDTO(UserPostRequestDTO userData) {
        UserEnitity userEnitity = new UserEnitity();
        userEnitity.setName(userData.getName());
        userEnitity.setEmailId(userData.getEmailId());
        userEnitity.setProfileImageLink(userData.getUserLogoUrl());
        userEnitity.setPassword(userData.getPassword());
        userEnitity.setCreateTimestamp(new Date());
        userEnitity.setUpdateTimestamp(new Date());

        return userEnitity;
    }

}
