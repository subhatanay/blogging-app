package com.scaler.bloggingapp.comments.entity;

import com.scaler.bloggingapp.blogs.entity.BlogEntity;
import com.scaler.bloggingapp.users.entity.UserEnitity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name="comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(targetEntity = UserEnitity.class)
    private UserEnitity user;

    @ManyToOne(targetEntity = BlogEntity.class)
    private BlogEntity article;

    private String commentData;
    private Long parentCommentId;


}
