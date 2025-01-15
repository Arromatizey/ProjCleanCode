package com.example.ESGI.service;

import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    public Article createArticle(Article article) {
        User author = userRepository.findById(article.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Auteur non trouvé"));

        article.setAuthor(author);
        article.setPublicationDate(LocalDateTime.now());

        return articleRepository.save(article);
    }

    public Article updateArticle(Long articleId, String title, String content) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
        article.setTitle(title);
        article.setContent(content);
        return articleRepository.save(article);
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public List<Article> searchArticles(String keyword) {
        return articleRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public List<Article> searchArticlesByAuthor(Long authorId) {
        return articleRepository.findByAuthorId(authorId);
    }

    public List<Article> searchArticlesByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return articleRepository.findByPublicationDateBetween(startDate, endDate);
    }
}

