package com.scaler.bloggingapp.feed.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties
public interface FeedArticleContent {
    public Long getArticleId();
    public String getDescription();
    public String getSubject();
    public String getContent();
    public Long getLikesCount();
    public  Long getUserId();
    public boolean isLiked();
    public String getUserName();
    public String getFullName();
    public String getCreatedAt();
}
