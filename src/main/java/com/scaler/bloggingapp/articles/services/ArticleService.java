package com.scaler.bloggingapp.articles.services;

import com.scaler.bloggingapp.articles.dtos.ArticleGetResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostRequestDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePutRequestDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;

public interface ArticleService {

    public abstract ArticlePostResponseDTO createArticle(Long userId, ArticlePostRequestDTO articlePostRequestDTO);

    public PagedResults<ArticleGetResponseDTO> getUserPostedArticles(Long userId, Integer offset, Integer pageSize);

    public ArticleGetResponseDTO getArticleByArticleIdAndUserId(Long userId, Long articleId);

    public boolean deleteArticleByArticleIdAndUserId(Long userId, Long articleId);

    public ArticleGetResponseDTO updateArticleByArticleIdAndUserId(Long userId, Long articleId, ArticlePutRequestDTO articlePutRequestDTO);

    public boolean likeArticle(Long userId, Long articleId);

    public boolean disLikeArticle(Long userId, Long articleId);

}
