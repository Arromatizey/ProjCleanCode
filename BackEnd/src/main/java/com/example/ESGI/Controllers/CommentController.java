package com.example.ESGI.Controllers;

import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.Comment;
import com.example.ESGI.Repositories.CommentRepository;
import com.example.ESGI.Repositories.ArticleRepository;

import com.example.ESGI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200") // ou "*"
public class CommentController {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository , UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create-comment")
    public Comment createComment(
            @RequestParam String content,// Assuming it will be a String (e.g., "2025-01-15")
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long articleId) {

        Comment copiedComment = new Comment();

        copiedComment.setContent(content);
        copiedComment.setPublicationDate(LocalDateTime.now());; // Parse the date to a LocalDate object

        // If an author ID is provided, set the author of the comment
        if (authorId != null) {
            User author = userRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            copiedComment.setAuthor(author);
        }

        // If an article ID is provided, set the article of the comment
        if (articleId != null) {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new RuntimeException("Article not found"));
            copiedComment.setArticle(article);
        }

        // Save and return the created comment
        return commentRepository.save(copiedComment);
    }

    @GetMapping("/{articleId}")
    public List<Comment> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    // You can add more methods as needed
}
