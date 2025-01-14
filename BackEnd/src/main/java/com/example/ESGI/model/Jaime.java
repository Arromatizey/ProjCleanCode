package com.example.ESGI.model;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Data
@Table(name = "jaime")
public class Jaime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    // Getters and Setters
}
