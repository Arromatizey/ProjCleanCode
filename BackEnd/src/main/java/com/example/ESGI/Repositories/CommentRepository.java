package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;
import com.example.ESGI.Repositories.*;
import jakarta.persistence.*;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
<<<<<<< HEAD
    List<Comment> findByArticleId(Long articleId);
=======
>>>>>>> e7755f4cb8b85bc75edf2436b409fb1a9ffa52ef
}
