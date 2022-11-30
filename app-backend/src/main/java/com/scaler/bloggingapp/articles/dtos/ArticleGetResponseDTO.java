package com.scaler.bloggingapp.articles.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleGetResponseDTO {
    private Long articleId;
    private String description;
    private String subject;
    private String content;

    private Long likesCount;
    private String createdAt;

    private boolean liked;


    private UserGetResponseDTO author;


    public static ArticleGetResponseDTO buildFrom(ArticleEntity article) {
        ArticleGetResponseDTO newArticle = new ArticleGetResponseDTO();
        newArticle.articleId = article.getArticleId();
        newArticle.description = article.getDescription();
        newArticle.subject = article.getSubject();
        newArticle.content = article.getContent();
        newArticle.createdAt = article.getCreateTimestamp().toString();


        return newArticle;
    }
}
