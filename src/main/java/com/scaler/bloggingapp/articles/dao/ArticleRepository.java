package com.scaler.bloggingapp.articles.dao;

import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.articles.entity.LikedArticleInfo;
import com.scaler.bloggingapp.feed.dtos.FeedArticleContent;
import com.scaler.bloggingapp.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    public Page<ArticleEntity> findAllByAuthor(UserEntity author, Pageable pageable);

    public Optional<ArticleEntity> findByArticleIdAndAuthor(Long articleId, UserEntity user);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(user_id) FROM likes where article_id=?1")
    public Long countLikesByArticleId(Long articleId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM likes WHERE article_id=?1")
    public void deleteLikesFromArticles(Long articleId);

    @Query(nativeQuery = true,
            value = "SELECT art.article_id AS articleId,subject, description,content,COALESCE(like_count, 0) as likesCount, art.create_timestamp AS createdAt,us.user_id AS userId , us.username AS userName, us.full_name AS fullName \n" +
                    "FROM articles art \n" +
                    "INNER join likes li on art.article_id=li.article_id\n" +
                    "INNER join users us on us.user_id=art.author_user_id\n" +
                    "LEFT JOIN (SELECT article_id,COUNT(user_id) AS like_count FROM likes WHERE user_id=?1  GROUP BY article_id ) likeddata \n" +
                    "ON likeddata.article_id=art.article_id\n" +
                    "WHERE li.user_id=?1 \n-- #page\n",
            countQuery = "SELECT count(*) \n" +
                    "FROM articles art \n" +
                    "INNER join likes li on art.article_id=li.article_id\n" +
                    "INNER join users us on us.user_id=art.author_user_id\n" +
                    "WHERE li.user_id=?1")
    public Page<LikedArticleInfo> findLikedArticlesByUser(Long userId, Pageable page);
}
