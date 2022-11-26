package com.scaler.bloggingapp.feed.services;

import com.scaler.bloggingapp.articles.dtos.ArticleGetResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.feed.dtos.FeedArticleContent;

public interface FeedService {

    public abstract PagedResults<FeedArticleContent> getTopFeeds(Integer limit, Integer offset);

    public abstract PagedResults<FeedArticleContent> getMyFeeds(Long userId, Integer limit, Integer offset);

}
