package com.example.ESGI.Controllers;

import com.example.ESGI.model.Comment;
import com.example.ESGI.service.CommentService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

    @GetMapping("/{articleId}")
    public List<Comment> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }

}
