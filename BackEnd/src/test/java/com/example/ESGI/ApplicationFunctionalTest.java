package com.example.ESGI;

import com.example.ESGI.Repositories.ArticleRepository;
import com.example.ESGI.Repositories.CommentRepository;
import com.example.ESGI.Repositories.UserRepository;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ApplicationFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testUserArticleCommentWorkflow() throws Exception {
        String userJson = """
            {
                "name": "John Doe",
                "email": "johndoe@example.com",
                "motDePasse": "password123"
            }
            """;

        MvcResult userResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andReturn();

        String userResponse = userResult.getResponse().getContentAsString();
        Integer userIdInteger = JsonPath.read(userResponse, "$.id");
        Long userId = userIdInteger.longValue();

        String articleJson = """
            {
                "title": "Test Article",
                "content": "This is a functional test article.",
                "author": {"id": %d}
            }
            """.formatted(userId);

        MvcResult articleResult = mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleJson))
                .andExpect(status().isOk())
                .andReturn();

        String articleResponse = articleResult.getResponse().getContentAsString();
        Integer articleIdInteger = JsonPath.read(articleResponse, "$.id");
        Long articleId = articleIdInteger.longValue();
        String commentJson = """
            {
                "content": "This is a test comment.",
                "author": {"id": %d},
                "article": {"id": %d}
            }
            """.formatted(userId, articleId);

        MvcResult commentResult = mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isOk())
                .andReturn();

        String commentResponse = commentResult.getResponse().getContentAsString();
        Integer commentIdInteger = JsonPath.read(commentResponse, "$.id");
        Long commentId = commentIdInteger.longValue();


        mockMvc.perform(get("/api/comments/" + articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(commentId))
                .andExpect(jsonPath("$[0].content").value("This is a test comment."))
                .andExpect(jsonPath("$[0].author.id").value(userId));
    }
}

