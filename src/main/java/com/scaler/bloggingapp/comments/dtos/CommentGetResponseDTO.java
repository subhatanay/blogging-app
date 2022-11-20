package com.scaler.bloggingapp.comments.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.bloggingapp.comments.entity.CommentsEntity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentGetResponseDTO {
    private Long commentId;
    private String commentData;
    private List<CommentGetResponseDTO> replyComments;


    public static CommentGetResponseDTO from(CommentsEntity commentsEntity) {
        CommentGetResponseDTO commentGetResponseDTO = new CommentGetResponseDTO();
        commentGetResponseDTO.setCommentId(commentsEntity.getCommentId());
        commentGetResponseDTO.setCommentData(commentsEntity.getCommentData());

        return commentGetResponseDTO;
    }
}
