package com.example.ESGI;

import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.JaimeRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.Jaime;
import com.example.ESGI.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JaimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JaimeRepository jaimeRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Article article;
    private Jaime jaime;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("testUser");
        user.setEmail("testuser2@example.com");
        user.setMotDePasse("password123");
        user = userRepository.save(user);

        article = new Article();
        article.setTitle("Test Article");
        article = articleRepository.save(article);

        jaime = new Jaime();
        jaime.setUser(user);
        jaime.setArticle(article);
    }

    @Test
    void testCreateJaime() throws Exception {
        String jaimeJson = "{ \"user\": {\"id\": " + user.getId() + "}, \"article\": {\"id\": " + article.getId() + "} }";

        mockMvc.perform(post("/api/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jaimeJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.article.id").value(article.getId()));
    }

    @Test
    void testGetLikesByArticleId() throws Exception {
        jaimeRepository.save(jaime);

        mockMvc.perform(get("/api/likes/{articleId}", article.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].article.id").value(article.getId()));
    }

    @Test
    void testHasUserLiked() throws Exception {
        jaimeRepository.save(jaime);

        mockMvc.perform(get("/api/likes/check/{articleId}/{userId}", article.getId(), user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testHasUserNotLiked() throws Exception {
        mockMvc.perform(get("/api/likes/check/{articleId}/{userId}", article.getId(), user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
