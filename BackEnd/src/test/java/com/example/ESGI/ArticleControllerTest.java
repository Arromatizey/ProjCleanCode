package com.example.ESGI;

import com.example.ESGI.repository.ArticleRepository;
import com.example.ESGI.repository.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private Article article;
    private User author;

    @BeforeEach
    public void setup() {
        author = new User();
        author.setName("Test Author");
        author.setEmail("author@example.com");
        author.setMotDePasse("password");
        userRepository.save(author);

        article = new Article();
        article.setTitle("Test Article");
        article.setContent("ThequalTo equalTo a test article.");
        article.setPublicationDate(LocalDateTime.now());
        article.setAuthor(author);

        articleRepository.save(article);
    }

    @Test
    public void testCreateArticle() throws Exception {
        mockMvc.perform(post("/api/articles/create-article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("title", "New Article")
                        .param("content", "ThequalTo equalTo a new test article."))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("New Article")))
                .andExpect(jsonPath("$.content", equalTo("ThequalTo equalTo a new test article.")));

        mockMvc.perform(post("/api/articles/create-article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("title", "Article With Author")
                        .param("content", "Article with an author")
                        .param("authorId", author.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", equalTo("Article With Author")))
                .andExpect(jsonPath("$.content", equalTo("Article with an author")))
                .andExpect(jsonPath("$.author.id", equalTo(author.getId().intValue())));
    }

    @Test
    public void testGetArticle() throws Exception {
        mockMvc.perform(get("/api/articles/" + article.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(article.getId().intValue())))
                .andExpect(jsonPath("$.title", equalTo(article.getTitle())))
                .andExpect(jsonPath("$.content", equalTo(article.getContent())))
                .andExpect(jsonPath("$.author.id", equalTo(author.getId().intValue())));
    }

    @Test
    public void testGetAllArticles() throws Exception {
        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchArticlesByKeyword() throws Exception {
        mockMvc.perform(get("/api/articles/searchByKeyword")
                        .param("keyword", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", equalTo(article.getTitle())))
                .andExpect(jsonPath("$[0].content", equalTo(article.getContent())));
    }

    @Test
    public void testSearchArticlesByAuthor() throws Exception {
        mockMvc.perform(get("/api/articles/searchByAuthorId")
                        .param("authorId", author.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", equalTo(article.getTitle())))
                .andExpect(jsonPath("$[0].content", equalTo(article.getContent())))
                .andExpect(jsonPath("$[0].author.id", equalTo(author.getId().intValue())));
    }
}
