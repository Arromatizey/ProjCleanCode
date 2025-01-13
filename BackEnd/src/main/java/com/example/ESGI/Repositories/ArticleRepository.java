package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;
import com.example.ESGI.Repositories.*;
import jakarta.persistence.*;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
