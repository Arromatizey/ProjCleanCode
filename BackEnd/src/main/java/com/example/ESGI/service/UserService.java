package com.example.ESGI.service;

import com.example.ESGI.Repositories.UserRepository;
import com.example.ESGI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

