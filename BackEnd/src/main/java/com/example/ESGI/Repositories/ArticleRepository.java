package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleContainingOrContentContaining(String title, String content);
    List<Article> findByAuthorId(Long authorId);
    List<Article> findByPublicationDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
