package com.scaler.bloggingapp.articles.services;

import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.articles.dao.ArticleRepository;
import com.scaler.bloggingapp.articles.dtos.ArticleGetResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostRequestDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePutRequestDTO;
import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.users.dao.UserRepository;
import com.scaler.bloggingapp.users.entity.UserEntity;
import com.scaler.bloggingapp.users.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private UserRepository userRepository;

    private ArticleRepository articleRepository;

    public ArticleServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public ArticlePostResponseDTO createArticle(Long userId, ArticlePostRequestDTO articlePostRequestDTO) {
        articlePostRequestDTO.validate();
        Optional<UserEntity> userInfo = userRepository.findById(userId);
        if (!userInfo.isPresent()) {
            throw new UserNotFoundException(MessageFormat.format("User with Id {0} not found" , userId));
        }


        ArticleEntity articleInfo = ArticleEntity.buildArticleEntityFromDTO(articlePostRequestDTO);
        articleInfo.setAuthor(userInfo.get());
        userInfo.get().getPublishedArticles().add(articleInfo);

        articleInfo = this.articleRepository.save(articleInfo);
        this.userRepository.save(userInfo.get());
        return ArticlePostResponseDTO.builder().articleId(articleInfo.getArticleId()).build();
    }

    @Override
    public PagedResults<ArticleGetResponseDTO> getUserPostedArticles(Long userId, Integer offset, Integer pageSize) {
        UserEntity userInfo = findByUserId(userId);

        Page<ArticleEntity> usersArticles = articleRepository.findAllByAuthor(userInfo, Pageable.ofSize(pageSize).withPage(offset));
        if (usersArticles==null || usersArticles.getTotalElements() == 0 ){
            throw new ArticleNotFoundException("No Articles posted by the user");
        }
        List<ArticleGetResponseDTO> usersArticleList = usersArticles.get().map(article -> {
            ArticleGetResponseDTO responseDTO=  ArticleGetResponseDTO.buildFrom(article);
            responseDTO.setLikesCount(articleRepository.countLikesByArticleId(responseDTO.getArticleId()));
            return responseDTO;
        }).collect(Collectors.toList());

        return PagedResults.<ArticleGetResponseDTO>builder()
                .pageCount(usersArticles.getTotalPages())
                .totalCount((int) usersArticles.getTotalElements())
                .pageSize(usersArticleList.size())
                .results(usersArticleList)
                .build();
    }

    @Override
    public ArticleGetResponseDTO getArticleByArticleIdAndUserId(Long userId, Long articleId) {
        UserEntity userInfo = findByUserId(userId);
        ArticleEntity article = findByUserAndAArticleId(userInfo,articleId);
        ArticleGetResponseDTO articleGetResponseDTO = ArticleGetResponseDTO.buildFrom(article);
        articleGetResponseDTO.setLikesCount(articleRepository.countLikesByArticleId(articleGetResponseDTO.getArticleId()));

        return ArticleGetResponseDTO.buildFrom(article);
    }

    @Override
    public boolean deleteArticleByArticleIdAndUserId(Long userId, Long articleId) {
        UserEntity userInfo = findByUserId(userId);
        ArticleEntity article = findByUserAndAArticleId(userInfo,articleId);

        articleRepository.deleteLikesFromArticles(article.getArticleId());

        articleRepository.delete(article);
        return true;
    }

    @Override
    public ArticleGetResponseDTO updateArticleByArticleIdAndUserId(Long userId, Long articleId, ArticlePutRequestDTO articlePutRequestDTO) {
        articlePutRequestDTO.validate();

        UserEntity userInfo = findByUserId(userId);
        ArticleEntity article = findByUserAndAArticleId(userInfo,articleId);
        article.updateEntity(articlePutRequestDTO);
        articleRepository.save(article);

        return ArticleGetResponseDTO.buildFrom(article);
    }

    @Override
    public boolean likeArticle(Long userId, Long articleId) {
        UserEntity user = findByUserId(userId);
        ArticleEntity article = findByArticleId(articleId);

        if (!user.getLikedArticles().contains(article)) {
            user.getLikedArticles().add(article);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean disLikeArticle(Long userId, Long articleId) {
        UserEntity user = findByUserId(userId);
        ArticleEntity article = findByArticleId(articleId);

        if (user.getLikedArticles().contains(article)) {
            user.getLikedArticles().remove(article);

            userRepository.save(user);
            return true;
        }
        return false;
    }

    private UserEntity findByUserId(Long userId) {
        Optional<UserEntity> userInfo = userRepository.findById(userId);
        if (!userInfo.isPresent()) {
            throw new UserNotFoundException(MessageFormat.format("User with Id {0} not found" , userId));
        }

        return userInfo.get();
    }

    private ArticleEntity findByUserAndAArticleId(UserEntity user, Long articleId) {
        Optional<ArticleEntity> articleEntity = articleRepository.findByArticleIdAndAuthor(articleId,user);
        if (!articleEntity.isPresent()) {
            throw new ArticleNotFoundException(MessageFormat.format("Article with id {0} not found for user with id {1}" , user.getUserId(),articleId));
        }
        return articleEntity.get();
    }

    private ArticleEntity findByArticleId(Long articleId) {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(articleId);
        if (!articleEntity.isPresent()) {
            throw new ArticleNotFoundException(MessageFormat.format("Article with id {0} not found " , articleId));
        }
        return articleEntity.get();
    }



}
