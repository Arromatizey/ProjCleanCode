package com.example.ESGI.Controllers;
import com.example.ESGI.model.User;
import com.example.ESGI.Repositories.*;
<<<<<<< HEAD

=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
>>>>>>> e7755f4cb8b85bc75edf2436b409fb1a9ffa52ef
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
