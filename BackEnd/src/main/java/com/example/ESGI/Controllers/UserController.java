package com.example.ESGI.Controllers;
import com.example.ESGI.model.User;
import com.example.ESGI.Repositories.*;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200") // ou "*"
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        User copiedUser = new User();

        copiedUser.setName(user.getName());
        copiedUser.setEmail(user.getEmail());
        copiedUser.setMotDePasse(user.getMotDePasse());
        return userRepository.save(copiedUser);
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
                return user;  // Return the user object if credentials match
            }
        }
        return null;  // Return null if username or password is incorrect
    }
}

