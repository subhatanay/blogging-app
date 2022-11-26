package com.scaler.bloggingapp.articles.entity;

public interface LikedArticleInfo {
    public Long getArticleId();
    public String getDescription();
    public String getSubject();
    public String getContent();
    public Long getLikesCount();
    public  Long getUserId();
    public String getUserName();
    public String getFullName();
    public String getCreatedAt();
}
