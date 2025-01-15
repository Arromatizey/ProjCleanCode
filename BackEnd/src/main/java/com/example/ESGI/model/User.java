package com.example.ESGI.model;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String motDePasse;
}
