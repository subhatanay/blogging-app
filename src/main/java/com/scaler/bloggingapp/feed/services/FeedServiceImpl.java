package com.scaler.bloggingapp.feed.services;

import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.feed.dao.FeedRepository;
import com.scaler.bloggingapp.feed.dtos.FeedArticleContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceImpl implements FeedService {

    private FeedRepository feedRepository;

    public FeedServiceImpl(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public PagedResults<FeedArticleContent> getTopFeeds(Integer limit, Integer offset) {
        Page<FeedArticleContent> feedArticleContents = feedRepository.getTopFeeds(Pageable.ofSize(limit).withPage(offset));

        return PagedResults.<FeedArticleContent>builder()
                .results(feedArticleContents.getContent())
                .totalCount((int) feedArticleContents.getTotalElements())
                .pageSize(feedArticleContents.getTotalPages())
                .pageCount(feedArticleContents.getContent().size())
                .build();
    }

    @Override
    public PagedResults<FeedArticleContent> getMyFeeds(Long userId, Integer limit, Integer offset) {
        Page<FeedArticleContent> feedArticleContents = feedRepository.getMyFeeds(userId,Pageable.ofSize(limit).withPage(offset));
        return PagedResults.<FeedArticleContent>builder()
                .results(feedArticleContents.getContent())
                .totalCount((int) feedArticleContents.getTotalElements())
                .pageSize(feedArticleContents.getTotalPages())
                .pageCount(feedArticleContents.getContent().size())
                .build();
    }
}
