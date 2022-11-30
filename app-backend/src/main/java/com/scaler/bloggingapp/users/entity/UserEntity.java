package com.scaler.bloggingapp.users.entity;

import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.common.models.AuditEntity;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
import com.scaler.bloggingapp.users.dto.UserPutRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class UserEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String fullName;
    private String bio;
    @Column(name = "email_id", unique = true)
    private String emailId;
    private String password;
    private String profileImageLink;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="follow",
                joinColumns = @JoinColumn(name="followed_id"),
                inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<UserEntity> followers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> userRoles = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<UserEntity> followings;

    @OneToMany(mappedBy = "author")
    private Set<ArticleEntity> publishedArticles;

    @OneToMany
    @JoinTable(name="likes",
            joinColumns = @JoinColumn(name="user_id", unique = false),
            inverseJoinColumns = @JoinColumn(name = "article_id" , unique = false))
    private Set<ArticleEntity> likedArticles;

    public void removeLikesFromArticle(ArticleEntity article) {
        likedArticles.remove(article);
    }


    public static UserEntity buildFromUserDTO(UserPostRequestDTO userData) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userData.getUsername());
        userEntity.setEmailId(userData.getEmailId());
        userEntity.setPassword(userData.getPassword());
        userEntity.setCreateTimestamp(new Date());
        userEntity.setUpdateTimestamp(new Date());

        return userEntity;
    }

    public UserEntity buildFromUserPutDto(UserPutRequestDto userData) {
        if (userData.getFullName()!=null) {
            this.setFullName(userData.getFullName());
        }
        if (userData.getProfileImageLink()!=null) {
            this.setProfileImageLink(userData.getProfileImageLink());
        }
        if (userData.getPassword()!=null) {
            this.setPassword(userData.getPassword());
        }
        if (userData.getBio()!=null) {
            this.setBio(userData.getBio());
        }
        return this;
    }

    public List<String> getRoles() {
        List<String> userRoles = new ArrayList<>();

        for (RolesEntity role: this.getUserRoles()) {
            userRoles.add(role.getRoleName());
        }

        return userRoles;
    }


}
