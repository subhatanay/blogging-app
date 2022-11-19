package com.scaler.bloggingapp.blogs.entity;

import com.scaler.bloggingapp.blogs.dtos.BlogPostRequestDTO;
import com.scaler.bloggingapp.comments.entity.CommentsEntity;
import com.scaler.bloggingapp.common.models.AuditEntity;
import com.scaler.bloggingapp.users.entity.UserEnitity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    public String description;
    private String content;

    @ManyToOne(targetEntity = UserEnitity.class)
    private UserEnitity author;


    @OneToMany(mappedBy = "article")
    private Set<CommentsEntity> articleComments;

    public static ArticleEntity buildBlogEntityFromDTO(BlogPostRequestDTO blogPostRequestDTO) {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setSubject(blogPostRequestDTO.getSubject());
        articleEntity.setContent(articleEntity.getContent());

        return articleEntity;
    }


}
