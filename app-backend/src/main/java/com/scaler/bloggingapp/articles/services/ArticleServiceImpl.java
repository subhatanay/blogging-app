package com.scaler.bloggingapp.articles.services;

import com.scaler.bloggingapp.articles.entity.LikedArticleInfo;
import com.scaler.bloggingapp.articles.exceptions.ArticleNotFoundException;
import com.scaler.bloggingapp.articles.dao.ArticleRepository;
import com.scaler.bloggingapp.articles.dtos.ArticleGetResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostRequestDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePostResponseDTO;
import com.scaler.bloggingapp.articles.dtos.ArticlePutRequestDTO;
import com.scaler.bloggingapp.articles.entity.ArticleEntity;
import com.scaler.bloggingapp.common.dto.PagedResults;
import com.scaler.bloggingapp.feed.dtos.FeedArticleContent;
import com.scaler.bloggingapp.users.dto.UserGetResponseDTO;
import com.scaler.bloggingapp.users.entity.dao.UserRepository;
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
            UserGetResponseDTO authorInfo = UserGetResponseDTO.buildFrom(article.getAuthor());
            ArticleGetResponseDTO responseDTO=  ArticleGetResponseDTO.buildFrom(article);
            responseDTO.setLikesCount(articleRepository.countLikesByArticleId(responseDTO.getArticleId()));
            responseDTO.setAuthor(authorInfo);
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
    public ArticleGetResponseDTO getArticle(Long articleId) {
        ArticleEntity articleEntity = findByArticleId(articleId);
        ArticleGetResponseDTO articleDTO = ArticleGetResponseDTO.buildFrom(articleEntity);
        UserGetResponseDTO authorInfo = UserGetResponseDTO.buildFrom(articleEntity.getAuthor());
        articleDTO.setLikesCount(articleRepository.countLikesByArticleId(articleEntity.getArticleId()));
        articleDTO.setAuthor(authorInfo);


        return articleDTO;
    }

    @Override
    public ArticleGetResponseDTO getArticleByArticleIdAndUserId(Long userId, Long articleId) {
        UserEntity userInfo = findByUserId(userId);
        ArticleEntity article = findByArticleId(articleId);
        ArticleGetResponseDTO articleGetResponseDTO = ArticleGetResponseDTO.buildFrom(article);
        articleGetResponseDTO.setLikesCount(articleRepository.countLikesByArticleId(articleGetResponseDTO.getArticleId()));
        articleGetResponseDTO.setAuthor(UserGetResponseDTO.buildFrom(article.getAuthor()));
        articleGetResponseDTO.setLiked(userInfo.getLikedArticles().contains(article));

        return articleGetResponseDTO;
    }

    @Override
    public boolean deleteArticleByArticleIdAndUserId(Long userId, Long articleId) {
        UserEntity userInfo = findByUserId(userId);
        ArticleEntity article = findByUserAndAArticleId(userInfo,articleId);

        articleRepository.deleteLikesFromArticles(article.getArticleId());

        articleRepository.delete(article);
        return true;
    }

    public boolean deleteArticleByArticleId( Long articleId) {
        ArticleEntity article = findByArticleId(articleId);
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

    @Override
    public PagedResults<LikedArticleInfo> likedArticlesByUser(Long userId, Integer limit, Integer offset) {
        findByUserId(userId);
        Page<LikedArticleInfo> articleEntityPage = articleRepository.findLikedArticlesByUser(userId, Pageable.ofSize(limit).withPage(offset));
        return PagedResults.<LikedArticleInfo>builder()
                .results(articleEntityPage.getContent())
                .totalCount((int) articleEntityPage.getTotalElements())
                .pageSize(articleEntityPage.getContent().size())
                .pageCount(articleEntityPage.getTotalPages())
                .build();
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
