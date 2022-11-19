package com.scaler.bloggingapp.blogs.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogGetResponseDTO {
    private Long blogId;
    private String subject;
    private String content;
}
