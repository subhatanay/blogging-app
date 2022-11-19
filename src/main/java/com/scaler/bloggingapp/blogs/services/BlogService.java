package com.scaler.bloggingapp.blogs.services;

import com.scaler.bloggingapp.blogs.dtos.BlogGetResponseDTO;
import com.scaler.bloggingapp.blogs.dtos.BlogPostRequestDTO;
import com.scaler.bloggingapp.blogs.dtos.BlogPostResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;

public interface BlogService {

    public abstract BlogPostResponseDTO createBlog(Long userId,BlogPostRequestDTO blogPostRequestDTO);

    public abstract PagedResults<BlogGetResponseDTO> getBlogsByUser(Long userId);

}
