package com.scaler.bloggingapp.comments.entity;

import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.comments.dtos.CommentPostRequestDTO;
import com.scaler.bloggingapp.common.models.AuditEntity;
import com.scaler.bloggingapp.users.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name="comments")
public class CommentsEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = ArticleEntity.class)
    @JoinColumn(name="article_id")
    private ArticleEntity article;

    private String commentData;

    @ManyToOne(targetEntity =  CommentsEntity.class)
    @JoinColumn(name="parent_comment_id")
    private CommentsEntity parentCommentId;

    public static CommentsEntity buildFrom(CommentPostRequestDTO commentPostRequestDTO, ArticleEntity article , UserEntity user) {
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setCommentData(commentPostRequestDTO.getCommentData());
        commentsEntity.setArticle(article);
        commentsEntity.setUser(user);
        commentsEntity.setCreateTimestamp(new Date());
        commentsEntity.setUpdateTimestamp(new Date());

        return commentsEntity;
    }

    public static CommentsEntity buildFrom(CommentPostRequestDTO commentPostRequestDTO, ArticleEntity article, UserEntity user, CommentsEntity parentComment) {
        CommentsEntity commentsEntity = buildFrom(commentPostRequestDTO,article, user);
        commentsEntity.setParentCommentId(parentComment);

        return commentsEntity;
    }



}
