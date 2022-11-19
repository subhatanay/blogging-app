package com.scaler.bloggingapp.comments.entity;

import com.scaler.bloggingapp.blogs.entity.ArticleEntity;
import com.scaler.bloggingapp.common.models.AuditEntity;
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
public class CommentsEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(targetEntity = UserEnitity.class)
    @JoinColumn(name="user_id")
    private UserEnitity user;

    @ManyToOne(targetEntity = ArticleEntity.class)
    @JoinColumn(name="article_id")
    private ArticleEntity article;

    private String commentData;

    @ManyToOne(targetEntity =  CommentsEntity.class)
    @JoinColumn(name="parent_comment_id")
    private CommentsEntity parentCommentId;


}
