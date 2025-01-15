package com.example.ESGI.Controllers;
import com.example.ESGI.model.User;
import com.example.ESGI.Repositories.*;

import com.example.ESGI.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return Optional.ofNullable(userService.getUserById(id));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
