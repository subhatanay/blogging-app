package com.scaler.bloggingapp.articles.dao;

import com.scaler.bloggingapp.articles.entity.ArticleEntity;
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

}
