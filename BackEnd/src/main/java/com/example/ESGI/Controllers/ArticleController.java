package com.example.ESGI.Controllers;
import com.example.ESGI.model.Article;

import com.example.ESGI.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.createArticle(article);
    }

    @GetMapping("/{id}")
    public Optional<Article> getArticle(@PathVariable Long id) {
        return Optional.ofNullable(articleService.getArticleById(id));
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }
}
