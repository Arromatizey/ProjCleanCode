package com.example.ESGI.Controllers;
import com.example.ESGI.model.Article;
import com.example.ESGI.Repositories.*;

import com.example.ESGI.model.User;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create-article")
    public Article createArticle(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(required = false) Long authorId) {

        Article copiedArticle = new Article();

        copiedArticle.setTitle(title);
        copiedArticle.setContent(content);
        copiedArticle.setPublicationDate(LocalDateTime.now());

        // If authorId is provided, set the author of the article
        if (authorId != null) {
            User author = userRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            copiedArticle.setAuthor(author);
        }

        return articleRepository.save(copiedArticle);
    }
    @GetMapping("/{id}")
    public Optional<Article> getArticle(@PathVariable Long id) {
        return articleRepository.findById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @GetMapping("/searchByKeyword")
    public List<Article> searchArticles(@RequestParam String keyword) {
        return articleRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }
    @GetMapping("/searchByAuthorId")
    public List<Article> searchArticlesByAuthor(@RequestParam Long authorId) {
        return articleRepository.findByAuthorId(authorId);
    }

}
