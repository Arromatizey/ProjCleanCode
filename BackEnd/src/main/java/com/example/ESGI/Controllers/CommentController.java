package com.example.ESGI.Controllers;

import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.Comment;
import com.example.ESGI.Repositories.CommentRepository;
import com.example.ESGI.Repositories.ArticleRepository;

import com.example.ESGI.model.User;

import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;


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
            @RequestParam String content,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long articleId) {

        Comment copiedComment = new Comment();

        copiedComment.setContent(content);
        copiedComment.setPublicationDate(LocalDateTime.now());
        if (authorId != null) {
            User author = userRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            copiedComment.setAuthor(author);
        }

        if (articleId != null) {
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new RuntimeException("Article not found"));
            copiedComment.setArticle(article);
        }

        return commentRepository.save(copiedComment);
    }

    @GetMapping("/{articleId}")
    public List<Comment> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

}
