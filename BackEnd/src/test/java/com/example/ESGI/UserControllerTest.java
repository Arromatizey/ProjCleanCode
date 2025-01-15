package com.example.ESGI;

import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setMotDePasse("password123");
        userRepository.save(user);
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/api/users/create-user")
                        .param("name", "New User")
                        .param("email", "newuser@example.com")
                        .param("motDePasse", "newpassword123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("New User")))
                .andExpect(jsonPath("$.email", is("newuser@example.com")));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/api/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].name", notNullValue()))
                .andExpect(jsonPath("$[0].email", notNullValue()));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .param("email", user.getEmail())
                        .param("password", user.getMotDePasse())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .param("email", user.getEmail())
                        .param("password", "wrongpassword")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
