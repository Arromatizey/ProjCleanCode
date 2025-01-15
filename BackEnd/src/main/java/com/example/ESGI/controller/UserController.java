package com.example.ESGI.controller;
import com.example.ESGI.model.User;
import com.example.ESGI.repository.*;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create-user")
    public User createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String motDePasse) {

        User copiedUser = new User();
        copiedUser.setName(name);
        copiedUser.setEmail(email);
        copiedUser.setMotDePasse(motDePasse);
        userRepository.save(copiedUser);
        return copiedUser;
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getMotDePasse().equals(password)) {
                return user;
            }
        }
        return null;
    }
}

