package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;

import com.example.ESGI.Repositories.*;
import jakarta.persistence.*;
import lombok.*;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
