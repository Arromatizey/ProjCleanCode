package com.example.ESGI.Controllers;

import com.example.ESGI.model.Comment;
import com.example.ESGI.Repositories.CommentRepository;
import com.example.ESGI.Repositories.ArticleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentRepository.save(comment);
    }

    @GetMapping("/{articleId}")
    public List<Comment> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    // You can add more methods as needed
}
