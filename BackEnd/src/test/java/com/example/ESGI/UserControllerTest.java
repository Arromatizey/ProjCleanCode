package com.example.ESGI;

import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setName("ccse");
        user.setEmail("ejrghb@gmail.com");
        user.setMotDePasse("hello123");
    }

    @Test
    void testCreateUser() throws Exception {
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ccse"))
                .andExpect(jsonPath("$.email").value("ejrghb@gmail.com"))
                .andExpect(jsonPath("$.motDePasse").value("hello123"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testLogin_Success() throws Exception {
        userRepository.save(user);
        mockMvc.perform(post("/api/users/login")
                        .param("email", user.getEmail())
                        .param("password", user.getMotDePasse()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ccse"))
                .andExpect(jsonPath("$.email").value("ejrghb@gmail.com")); // Vérifie l'email
    }

    @Test
    public void testLogin_Failure_WrongPassword() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .param("email", "test@example.com")
                        .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testLogin_Failure_NonExistentUser() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .param("email", "nonexistent@example.com")
                        .param("password", "anyPassword"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}

  
