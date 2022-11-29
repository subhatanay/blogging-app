package com.scaler.bloggingapp.comments.services;

import com.scaler.bloggingapp.articles.dao.ArticleRepository;
import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.comments.dao.CommentsRepository;
import com.scaler.bloggingapp.comments.dtos.CommentGetResponseDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPostRequestDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPostResponseDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPutRequestDTO;
import com.scaler.bloggingapp.comments.entity.CommentsEntity;
import com.scaler.bloggingapp.comments.exceptions.CommentNotAllowedException;
import com.scaler.bloggingapp.comments.exceptions.NoCommentFoundException;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.entity.dao.UserRepository;
import com.scaler.bloggingapp.users.entity.UserEntity;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements  CommentsService{
    private CommentsRepository commentsRepository;
    UserRepository userRepository;
    ArticleRepository articleRepository;

    public CommentsServiceImpl(CommentsRepository commentsRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentsRepository = commentsRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentPostResponseDTO createComment(Long userId, Long articleId, CommentPostRequestDTO commentPostRequestDTO) {
        commentPostRequestDTO.validate();
        UserEntity user = findByUserId(userId);
        ArticleEntity article = findByArticleId(articleId);
        CommentsEntity parentComment = null;

        if (commentPostRequestDTO.getParentCommentId()!=null) {
            Optional<CommentsEntity> parentCommentEntity = this.commentsRepository.findById(commentPostRequestDTO.getParentCommentId());
            if (!parentCommentEntity.isPresent()) {
                throw new NoCommentFoundException("Parent comment not found.");
            }
            parentComment = parentCommentEntity.get();

            if (parentComment.getParentCommentId() != null) {
                throw new CommentNotAllowedException("Operation not allowed. Reply on comment is allowed only one level.");
            }
        }
        CommentsEntity currentComment = parentComment != null ? CommentsEntity.buildFrom(commentPostRequestDTO,article,user , parentComment) : CommentsEntity.buildFrom(commentPostRequestDTO,article,user);
        currentComment = this.commentsRepository.save(currentComment);

        return CommentPostResponseDTO.builder().commentId(currentComment.getCommentId()).build();
    }

    @Override
    public PagedResults<CommentGetResponseDTO> getCommentsByArticle(Long articleId, Integer offset, Integer limit) {
        ArticleEntity article = findByArticleId(articleId);
        Page<CommentsEntity> articleComments = this.commentsRepository.findCommentsByArticleId(article.getArticleId(), Pageable.ofSize(limit).withPage(offset));

        List<CommentGetResponseDTO> commentsListByArticle = articleComments.getContent().stream().map(artComm -> {
            CommentGetResponseDTO comment = CommentGetResponseDTO.from(artComm);
            List<CommentsEntity> replyCommentsEntity = this.commentsRepository.findReplyCommentsByArticleIdAndCommentId(articleId, comment.getCommentId());

            List<CommentGetResponseDTO> replyCommentList = replyCommentsEntity.stream().map(CommentGetResponseDTO::from).collect(Collectors.toList());
            if (replyCommentList.size()>0)
                comment.setReplyComments(replyCommentList);
            return comment;
        }).collect(Collectors.toList());

        return PagedResults.<CommentGetResponseDTO>builder()
                .totalCount((int) articleComments.getTotalElements())
                .pageSize(commentsListByArticle.size())
                .pageCount(articleComments.getTotalPages())
                .results(commentsListByArticle)
                .build();
    }

    @Override
    public boolean deleteComment(Long articleId, Long commentId) {
        CommentsEntity comment = findByArticleIdAndCommentId(articleId, commentId);
        List<CommentsEntity> replyComments = this.commentsRepository.findReplyCommentsByArticleIdAndCommentId(articleId,commentId);

        if (replyComments.size()>0) {
            this.commentsRepository.deleteAllInBatch(replyComments);
        }

        this.commentsRepository.delete(comment);
        return true;
    }

    public boolean isEligibleToDeleteComment(Long userId, Long commentId) {
        if (userId == null) {
            UserEntity user = findByUserId(userId);
            Optional<CommentsEntity> comment = this.commentsRepository.findByCommentIdAndUser(commentId , user);
            if (comment == null || !comment.isPresent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CommentGetResponseDTO updateCommentByCommentIdAndArticleId(Long articleId, Long commentId, CommentPutRequestDTO commentPutRequestDTO) {
        CommentsEntity comment =  findByArticleIdAndCommentId(articleId,commentId);
        comment.setCommentData(commentPutRequestDTO.getCommentsData());

        comment.setCommentData(commentPutRequestDTO.getCommentsData());
        comment.setUpdateTimestamp(new Date());
        this.commentsRepository.save(comment);

        return CommentGetResponseDTO.from(comment);
    }


    private CommentsEntity findByArticleIdAndCommentId(Long articleId, Long commentId) {
        Optional<CommentsEntity> commentsEntity = this.commentsRepository.findById(commentId);

        if (!commentsEntity.isPresent()) {
            throw new NoCommentFoundException("Comment not found");
        }
        if (!commentsEntity.get().getArticle().getArticleId().equals(articleId)) {
            throw new NoCommentFoundException("Comment not found");
        }
        return commentsEntity.get();
    }

    private UserEntity findByUserId(Long userId) {
        Optional<UserEntity> userInfo = userRepository.findById(userId);
        if (!userInfo.isPresent()) {
            throw new UserNotFoundException(MessageFormat.format("User with Id {0} not found" , userId));
        }

        return userInfo.get();
    }

    private ArticleEntity findByArticleId(Long articleId) {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(articleId);
        if (!articleEntity.isPresent()) {
            throw new ArticleNotFoundException(MessageFormat.format("Article with id {0} not found " , articleId));
        }
        return articleEntity.get();
    }
}
