package com.example.ESGI;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ApplicationFunctionalTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateUserAndLogin() throws Exception {
		mockMvc.perform(post("/api/users/create-user")
						.param("name", "Functional Test User")
						.param("email", "functionall@test.com")
						.param("motDePasse", "securepassword")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Functional Test User"))
				.andExpect(jsonPath("$.email").value("functionall@test.com"));

		mockMvc.perform(post("/api/users/login")
						.param("email", "functionall@test.com")
						.param("password", "securepassword")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Functional Test User"))
				.andExpect(jsonPath("$.email").value("functionall@test.com"));
	}

	@Test
	public void testCreateArticleAndComment() throws Exception {
		String userResponse = mockMvc.perform(post("/api/users/create-user")
						.param("name", "Article Test User")
						.param("email", "articleuserr@test.com")
						.param("motDePasse", "password123")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();
		JsonNode responseJson = objectMapper.readTree(userResponse);
		Long userId = responseJson.get("id").asLong();

		String articleResponse = mockMvc.perform(post("/api/articles/create-article")
						.param("title", "Functional Test Article")
						.param("content", "This is a test article.")
						.param("authorId", String.valueOf(userId))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();

		JsonNode articleJson = objectMapper.readTree(articleResponse);
		Long articleId = articleJson.get("id").asLong();


		mockMvc.perform(post("/api/comments/create-comment")
						.param("content", "Test Comment")
						.param("authorId", String.valueOf(userId))
						.param("articleId", String.valueOf(articleId))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Test Comment"))
				.andExpect(jsonPath("$.article.id").value(articleId));
	}

	@Test
	public void testLikeArticle() throws Exception {
		String userResponse = mockMvc.perform(post("/api/users/create-user")
						.param("name", "Like Test User")
						.param("email", "likeuserr@test.com")
						.param("motDePasse", "password123")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Long userId = objectMapper.readTree(userResponse).get("id").asLong(); // Parse et récupère l'ID utilisateur

		String articleResponse = mockMvc.perform(post("/api/articles/create-article")
						.param("title", "Like Test Article")
						.param("content", "This is a test article for liking.")
						.param("authorId", userId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Long articleId = objectMapper.readTree(articleResponse).get("id").asLong(); // Parse et récupère l'ID article

		mockMvc.perform(post("/api/likes/create-jaime")
						.param("userId", userId.toString())
						.param("articleId", articleId.toString())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.article.id").value(articleId.intValue())); // Vérifie l'ID de l'article dans la réponse
	}

}

