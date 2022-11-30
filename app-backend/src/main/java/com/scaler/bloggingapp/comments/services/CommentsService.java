package com.scaler.bloggingapp.comments.services;

import com.scaler.bloggingapp.comments.dtos.CommentGetResponseDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPostRequestDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPostResponseDTO;
import com.scaler.bloggingapp.comments.dtos.CommentPutRequestDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;

public interface CommentsService {
    abstract CommentPostResponseDTO createComment(Long userId, Long articleId, CommentPostRequestDTO commentPostRequestDTO);

    public abstract PagedResults<CommentGetResponseDTO> getCommentsByArticle(Long articleId, Integer offset, Integer limit);

    public  abstract boolean deleteComment(Long articleId, Long commentId);

    public abstract boolean isEligibleToDeleteComment(Long userId, Long commentId);

    public abstract CommentGetResponseDTO updateCommentByCommentIdAndArticleId(Long articleId, Long commentId, CommentPutRequestDTO commentPutRequestDTO);
}
