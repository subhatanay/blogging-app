package com.scaler.bloggingapp.users.entity;

import com.scaler.bloggingapp.blogs.entity.ArticleEntity;
import com.scaler.bloggingapp.common.models.AuditEntity;
import com.scaler.bloggingapp.users.dto.UserPostRequestDTO;
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
public class UserEnitity extends AuditEntity {
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
    private Set<UserEnitity> followers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> userRoles = new HashSet<>();

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
        userEnitity.setUsername(userData.getUsername());
        userEnitity.setEmailId(userData.getEmailId());
        userEnitity.setPassword(userData.getPassword());
        userEnitity.setCreateTimestamp(new Date());
        userEnitity.setUpdateTimestamp(new Date());

        return userEnitity;
    }

    public List<String> getRoles() {
        List<String> userRoles = new ArrayList<>();

        for (RolesEntity role: this.getUserRoles()) {
            userRoles.add(role.getRoleName());
        }

        return userRoles;
    }


}
