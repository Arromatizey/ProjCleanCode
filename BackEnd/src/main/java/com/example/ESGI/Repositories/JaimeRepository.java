
package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JaimeRepository extends JpaRepository<Jaime, Long> {
    List<Jaime> findByArticleId(Long articleId);
    Optional<Jaime> findByArticleIdAndUserId(Long articleId, Long userId);
    boolean existsByArticleIdAndUserId(Long articleId, Long userId);
}
