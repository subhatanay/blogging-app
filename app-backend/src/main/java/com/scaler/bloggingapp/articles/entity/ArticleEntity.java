package com.scaler.bloggingapp.articles.entity;

import com.scaler.bloggingapp.articles.dtos.ArticlePostRequestDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePutRequestDTO;
import com.scaler.bloggingapp.comments.entity.CommentsEntity;
import com.scaler.bloggingapp.common.models.AuditEntity;
import com.scaler.bloggingapp.users.entity.UserEntity;
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
@Entity(name = "articles")
public class ArticleEntity extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String subject;
    @Column(length = 500)
    public String description;
    @Column(length = 5000)
    private String content;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity author;


    @OneToMany(mappedBy = "article",cascade = CascadeType.REMOVE)
    private Set<CommentsEntity> articleComments;

    public static ArticleEntity buildArticleEntityFromDTO(ArticlePostRequestDTO articlePostRequestDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setSubject(articlePostRequestDTO.getSubject());
        articleEntity.setContent(articlePostRequestDTO.getContent());
        articleEntity.setDescription(articlePostRequestDTO.getDescription());
        articleEntity.setCreateTimestamp(new Date());
        articleEntity.setUpdateTimestamp(new Date());

        return articleEntity;
    }

    public void updateEntity(ArticlePutRequestDTO articlePutRequestDTO) {
        this.setUpdateTimestamp(new Date());

        if (articlePutRequestDTO.getDescription() != null) {
            this.setDescription(articlePutRequestDTO.getDescription());
        }
        if (articlePutRequestDTO.getSubject() != null) {
            this.setSubject(articlePutRequestDTO.getSubject());
        }

        if (articlePutRequestDTO.getContent() != null) {
            this.setContent(articlePutRequestDTO.getContent());
        }
    }


}
