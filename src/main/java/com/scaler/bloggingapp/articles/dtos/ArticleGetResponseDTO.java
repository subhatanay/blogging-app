package com.scaler.bloggingapp.articles.dtos;

import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleGetResponseDTO {
    private Long articleId;
    private String description;
    private String subject;
    private String content;

    private Long likesCount;

    private String createdAt;


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
