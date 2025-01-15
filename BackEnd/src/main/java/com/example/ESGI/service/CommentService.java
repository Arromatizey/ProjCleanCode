package com.example.ESGI.service;

import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.CommentRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.Comment;
import com.example.ESGI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public Comment createComment(Comment comment) {
        Article article = articleRepository.findById(comment.getArticle().getId())
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        User author = userRepository.findById(comment.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Auteur non trouvé"));

        comment.setArticle(article);
        comment.setAuthor(author);
        comment.setPublicationDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

