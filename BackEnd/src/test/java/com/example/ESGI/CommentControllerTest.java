package com.example.ESGI;

import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.CommentRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.Article;
import com.example.ESGI.model.Comment;
import com.example.ESGI.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private Article article;
    private Comment comment;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("testUser");
        user.setEmail("test@example.com");
        user.setMotDePasse("password123");
        user = userRepository.save(user);

        article = new Article();
        article.setTitle("Test Article");
        article = articleRepository.save(article);

        comment = new Comment();
        comment.setContent("Test comment");
        comment.setAuthor(user);
        comment.setArticle(article);
    }

    @Test
    void testCreateComment() throws Exception {
        mockMvc.perform(post("/api/comments/create-comment")
                        .param("content", "Test comment")
                        .param("authorId", String.valueOf(user.getId()))
                        .param("articleId", String.valueOf(article.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test comment"))
                .andExpect(jsonPath("$.article.title").value("Test Article"))
                .andExpect(jsonPath("$.author.name").value("testUser"));
    }

    @Test
    void testGetCommentsByArticleId() throws Exception {
        commentRepository.save(comment);

        mockMvc.perform(get("/api/comments/{articleId}", article.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Test comment"))
                .andExpect(jsonPath("$[0].article.title").value("Test Article"))
                .andExpect(jsonPath("$[0].author.name").value("testUser"));
    }
}
