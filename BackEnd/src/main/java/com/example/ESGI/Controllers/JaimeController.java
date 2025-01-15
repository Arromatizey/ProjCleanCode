package com.example.ESGI.Controllers;

import com.example.ESGI.model.Jaime;
import com.example.ESGI.Repositories.JaimeRepository;
import com.example.ESGI.Repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/likes")  // You could adjust the path if you want
@CrossOrigin(origins = "http://localhost:4200") // ou "*"
public class JaimeController {

    private final JaimeRepository jaimeRepository;
    private final ArticleRepository articleRepository;

    public JaimeController(JaimeRepository jaimeRepository, ArticleRepository articleRepository) {
        this.jaimeRepository = jaimeRepository;
        this.articleRepository = articleRepository;
    }

    // Endpoint to post a like ("jaime") on an article
    @PostMapping
    public Jaime createJaime(@RequestBody Jaime jaime) {
        return jaimeRepository.save(jaime);
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
