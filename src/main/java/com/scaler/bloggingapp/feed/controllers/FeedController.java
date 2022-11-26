package com.scaler.bloggingapp.feed.controllers;

import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.common.models.CurrentAuthenticationHolder;
import com.scaler.bloggingapp.feed.dtos.FeedArticleContent;
import com.scaler.bloggingapp.feed.services.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/feed")
public class FeedController {
    private FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping(path = "/top")
    public ResponseEntity getTopFeeds(@RequestParam(name = "limit" , defaultValue = "10") Integer limit, @RequestParam(name = "offset",defaultValue = "0") Integer offset) {
        PagedResults<FeedArticleContent> feedArticleContentPagedResults = feedService.getTopFeeds(limit,offset);

        return ResponseEntity.ok(feedArticleContentPagedResults);
    }

    @GetMapping(path = "/me")
    public ResponseEntity getMyFeeds(@RequestParam(name = "limit" , defaultValue = "10") Integer limit, @RequestParam(name = "offset",defaultValue = "0") Integer offset) {
        Long userId = CurrentAuthenticationHolder.getCurrentAuthenticationContext().getUserId();
        PagedResults<FeedArticleContent> feedArticleContentPagedResults = feedService.getMyFeeds(userId,limit,offset);

        return ResponseEntity.ok(feedArticleContentPagedResults);
    }
}
