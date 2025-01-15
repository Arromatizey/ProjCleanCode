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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("testuser2@example.com");
        user.setMotDePasse("password123");
        user = userRepository.save(user);

        article = new Article();
        article.setTitle("Test Article");
        article.setContent("This is a test article.");
        article = articleRepository.save(article);
    }

    @Test
    void testCreateJaime() throws Exception {
        mockMvc.perform(post("/api/likes/create-jaime")
                        .param("userId", String.valueOf(user.getId()))
                        .param("articleId", String.valueOf(article.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.article.id", is(article.getId().intValue())));
    }

    @Test
    void testGetLikesByArticleId() throws Exception {
        Jaime jaime = new Jaime();
        jaime.setUser(user);
        jaime.setArticle(article);
        jaimeRepository.save(jaime);

        mockMvc.perform(get("/api/likes/" + article.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$[0].article.id", is(article.getId().intValue())));
    }

    @Test
    void testHasUserLiked() throws Exception {
        Jaime jaime = new Jaime();
        jaime.setUser(user);
        jaime.setArticle(article);
        jaimeRepository.save(jaime);

        mockMvc.perform(get("/api/likes/check/{articleId}/{userId}", article.getId(), user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void testHasUserNotLiked() throws Exception {
        mockMvc.perform(get("/api/likes/check/{articleId}/{userId}", article.getId(), user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }
}
