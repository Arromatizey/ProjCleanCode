package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContainingOrContentContaining(String title, String content);
    List<Article> findByAuthorId(Long authorId);
}
