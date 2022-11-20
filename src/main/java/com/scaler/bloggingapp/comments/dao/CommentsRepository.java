package com.scaler.bloggingapp.comments.dao;

import com.scaler.bloggingapp.comments.entity.CommentsEntity;
import com.scaler.bloggingapp.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * from comments com INNER JOIN articles art ON com.article_id=art.article_id and art.article_id=?1 and parent_comment_id IS NULL order by art.article_id \n-- #page\n",
            countQuery = "SELECT * from comments com INNER JOIN articles art ON com.article_id=art.article_id and art.article_id=?1 and parent_comment_id IS NULL order by art.article_id"
    )
    public Page<CommentsEntity> findCommentsByArticleId(Long articleId, Pageable page);

    @Query(nativeQuery = true,
            value = "SELECT * from comments com INNER JOIN articles art ON com.article_id=art.article_id and art.article_id=?1 and parent_comment_id =?2 order by art.article_id"
    )
    public List<CommentsEntity> findReplyCommentsByArticleIdAndCommentId(Long articleId, Long commentId);

    public Optional<CommentsEntity> findByCommentIdAndUser(Long commentId, UserEntity user);
}
