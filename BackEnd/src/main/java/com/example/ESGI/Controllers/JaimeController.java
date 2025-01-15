package com.example.ESGI.Controllers;

import com.example.ESGI.model.Article;
import com.example.ESGI.model.Jaime;
import com.example.ESGI.Repositories.JaimeRepository;
import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/likes")  // You could adjust the path if you want
public class JaimeController {

    private final JaimeRepository jaimeRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository ;

    public JaimeController(JaimeRepository jaimeRepository, ArticleRepository articleRepository, UserRepository userRepository) {
        this.jaimeRepository = jaimeRepository;
        this.articleRepository = articleRepository;
        this.userRepository= userRepository;
    }

    // Endpoint to post a like ("jaime") on an article
    @PostMapping("/create-jaime")
    public Jaime createJaime(
            @RequestParam Long userId,
            @RequestParam Long articleId) {

        Jaime copiedJaime = new Jaime();


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        copiedJaime.setUser(user);
        copiedJaime.setArticle(article);
        return jaimeRepository.save(copiedJaime);
    }

    // Endpoint to get all likes for a specific article by articleId
    @GetMapping("/{articleId}")
    public List<Jaime> getLikesByArticleId(@PathVariable Long articleId) {
        return jaimeRepository.findByArticleId(articleId);
    }

    // Optional: Method to check if a user has already liked an article
    @GetMapping("/check/{articleId}/{userId}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long articleId, @PathVariable Long userId) {
        boolean hasLiked = jaimeRepository.findByArticleId(articleId).stream()
                .anyMatch(jaime -> jaime.getUser().getId().equals(userId));
        return ResponseEntity.ok(hasLiked);
    }
}
