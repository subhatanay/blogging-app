package com.scaler.bloggingapp.blogs.services;

import com.scaler.bloggingapp.blogs.dao.BlogRepository;
import com.scaler.bloggingapp.blogs.dtos.BlogGetResponseDTO;
import com.scaler.bloggingapp.blogs.dtos.BlogPostRequestDTO;
import com.scaler.bloggingapp.blogs.dtos.BlogPostResponseDTO;
import com.scaler.bloggingapp.blogs.entity.ArticleEntity;
import com.scaler.bloggingapp.common.dto.ErrorResponseDTO;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dao.UserRepository;
import com.scaler.bloggingapp.users.entity.UserEnitity;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;

import java.text.MessageFormat;
import java.util.Optional;

public class BlogServiceImpl implements BlogService {

    private UserRepository userRepository;

    private BlogRepository blogRepository;

    public BlogServiceImpl(UserRepository userRepository, BlogRepository blogRepository) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public BlogPostResponseDTO createBlog(Long userId, BlogPostRequestDTO blogPostRequestDTO) {
        try {
            Optional<UserEnitity> userInfo = userRepository.findById(userId);

            if (!userInfo.isPresent()) {
                throw new UserNotFoundException(MessageFormat.format("User with Id %s not found" , userId));
            }

            ArticleEntity blogInfo = ArticleEntity.buildBlogEntityFromDTO(blogPostRequestDTO);
            blogInfo.setAuthor(userInfo.get());

            blogInfo = this.blogRepository.save(blogInfo);
            return BlogPostResponseDTO.builder().blogId(blogInfo.getArticleId()).build();
        } catch (UserNotFoundException userNotFoundException) {
            return BlogPostResponseDTO.builder()
                    .errorResponse(ErrorResponseDTO
                            .builder()
                            .errorCode(userNotFoundException.getErrorCode())
                            .errorMessage(userNotFoundException.getMessage())
                            .build())
                    .build();
        } catch (Exception exception) {
            return BlogPostResponseDTO.builder()
                    .errorResponse(ErrorResponseDTO
                            .builder()
                            .errorCode(500)
                            .errorMessage(exception.getMessage())
                            .build())
                    .build();
        }

    }

    @Override
    public PagedResults<BlogGetResponseDTO> getBlogsByUser(Long userId) {
        return null;
    }
}
