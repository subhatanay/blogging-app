package com.scaler.bloggingapp.blogs.dao;

import com.scaler.bloggingapp.blogs.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<ArticleEntity, Long> {

}
