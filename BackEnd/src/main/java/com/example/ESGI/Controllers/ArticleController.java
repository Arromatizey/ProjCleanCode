package com.example.ESGI.Controllers;
import com.example.ESGI.model.Article;
import com.example.ESGI.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200") // ou "*"
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping("/{id}")
    public Optional<Article> getArticle(@PathVariable Long id) {
        return articleRepository.findById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
