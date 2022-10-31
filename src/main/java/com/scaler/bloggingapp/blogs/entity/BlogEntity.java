package com.scaler.bloggingapp.blogs.entity;

import com.scaler.bloggingapp.comments.entity.CommentsEntity;
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
@Entity(name = "blogs")
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;
    private String subject;
    private String content;

    @ManyToOne(targetEntity = UserEnitity.class)
    private UserEnitity author;


    @OneToMany(mappedBy = "article")
    private Set<CommentsEntity> articleComments;


}
