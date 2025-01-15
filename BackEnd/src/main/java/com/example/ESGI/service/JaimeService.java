package com.example.ESGI.service;

import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.JaimeRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.Jaime;
import com.example.ESGI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class JaimeService {

    @Autowired
    private JaimeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public Jaime likeArticle(Jaime jaime) {
        Article article = articleRepository.findById(jaime.getArticle().getId())
                .orElseThrow(() -> new RuntimeException("Article non trouvé"));

        User user = userRepository.findById(jaime.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        jaime.setArticle(article);
        jaime.setUser(user);

        return likeRepository.save(jaime);

    }

    public void unLikeArticle(Long articleId, Long userId) {
        Jaime like = likeRepository.findByArticleIdAndUserId(articleId, userId)
                .orElseThrow(() -> new RuntimeException("Like non trouvé"));
        likeRepository.delete(like);
    }

    public boolean hasLiked(Long articleId, Long userId) {
        return likeRepository.existsByArticleIdAndUserId(articleId, userId);
    }

    public List<Jaime> getLikesByArticleId (@PathVariable Long articleId){
        return likeRepository.findByArticleId(articleId);
    }


}

