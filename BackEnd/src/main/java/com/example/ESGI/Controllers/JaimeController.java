package com.example.ESGI.Controllers;

import com.example.ESGI.model.Jaime;
import com.example.ESGI.Repositories.JaimeRepository;
import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.service.JaimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class JaimeController {

    //private final JaimeRepository jaimeRepository;
    //private final ArticleRepository articleRepository;

    private final JaimeService jaimeService;

    public JaimeController(JaimeService jaimeService) {
        this.jaimeService = jaimeService;
    }

    @PostMapping
    public Jaime createJaime(@RequestBody Jaime jaime) {
        return jaimeService.likeArticle(jaime);
    }


    @GetMapping("/{articleId}")
    public List<Jaime> getLikesByArticleId(@PathVariable Long articleId) {
        return jaimeService.getLikesByArticleId(articleId);
    }

    @GetMapping("/check/{articleId}/{userId}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long articleId, @PathVariable Long userId) {
        boolean tmp = jaimeService.hasLiked(articleId, userId);
        return ResponseEntity.ok(tmp);
    }
}
