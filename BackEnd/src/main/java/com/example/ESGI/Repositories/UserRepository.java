package com.example.ESGI.Repositories;
import com.example.ESGI.model.*;



import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
