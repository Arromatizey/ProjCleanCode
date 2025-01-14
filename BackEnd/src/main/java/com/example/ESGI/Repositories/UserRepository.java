package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;

import com.example.ESGI.Repositories.*;
import jakarta.persistence.*;
import lombok.*;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
