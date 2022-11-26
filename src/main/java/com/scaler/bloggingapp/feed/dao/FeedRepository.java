package com.scaler.bloggingapp.feed.dao;

import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.feed.dtos.FeedArticleContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<ArticleEntity, Long> {

    @Query(nativeQuery = true,
            value = "SELECT art.article_id as articleId,subject, description,content,like_count as likesCount, art.create_timestamp as createdAt,us.user_id as userId , us.username as userName, us.full_name as fullName" +
                    " FROM articles art \n" +
                    "INNER JOIN (SELECT article_id,COUNT(user_id) AS like_count FROM  likes li WHERE user_id=?1  GROUP BY article_id ) la \n" +
                    "on art.article_id=la.article_id\n" +
                    "INNER JOIN users us on art.author_user_id=us.user_id\n" +
                    "order by like_count DESC \n-- #page\n",
            countQuery = "SELECT count(art)" +
                    " FROM articles art \n" +
                    "INNER JOIN (SELECT article_id,COUNT(user_id) AS like_count FROM  likes li GROUP BY article_id ) la \n" +
                    "on art.article_id=la.article_id\n" +
                    "INNER JOIN users us on art.author_user_id=us.user_id"
        )
    public Page<FeedArticleContent> getTopFeeds(Pageable page);

    @Query(nativeQuery = true,
           value = "SELECT art.article_id as articleId,subject, description,content,COALESCE(like_count, 0) as likesCount,(CASE WHEN art_likes IS NOT NULL THEN 'true' ELSE 'false' END) AS liked, art.create_timestamp as createdAt,us.user_id as userId , us.username as userName, us.full_name as fullName \n" +
                   "FROM follow fl \n" +
                   "INNER JOIN articles art ON fl.followed_id=art.author_user_id \n" +
                   "INNER JOIN users us ON us.user_id=art.author_user_id \n" +
                   "LEFT JOIN likes art_likes on art_likes.user_id=fl.follower_id and art_likes.article_id=art.article_id \n" +
                   "LEFT JOIN (SELECT article_id,COUNT(user_id) AS like_count FROM  likes li WHERE user_id=?1  GROUP BY article_id ) likeddata \n" +
                   "ON likeddata.article_id=art.article_id\n" +
                   "WHERE follower_id=?1 ORDER BY like_count DESC \n-- #page\n",
           countQuery = "SELECT art.article_id as articleId,subject, description,content,COALESCE(like_count, 0) as likesCount, art.create_timestamp as createdAt,us.user_id as userId , us.username as userName, us.full_name as fullName \n" +
                   "FROM follow fl \n" +
                   "INNER JOIN articles art ON fl.followed_id=art.author_user_id \n" +
                   "INNER JOIN users us ON us.user_id=art.author_user_id \n" +
                   "LEFT JOIN (SELECT article_id,COUNT(user_id) AS like_count FROM  likes li GROUP BY article_id ) likeddata \n" +
                   "ON likeddata.article_id=art.article_id\n" +
                   "WHERE follower_id=?1 ORDER BY like_count DESC ")
    public Page<FeedArticleContent> getMyFeeds(Long userId,Pageable page);
}
