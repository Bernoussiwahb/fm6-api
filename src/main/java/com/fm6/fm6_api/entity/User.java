package com.fm6.fm6_api.entity;

import jakarta.persistence.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity @Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String passwordHash;
    private String role;

    /* ---- GETTERS & SETTERS ---- */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
   

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public void setPasswordHash(String raw){
    this.passwordHash = new BCryptPasswordEncoder().encode(raw);
    }
    public boolean checkPassword(String raw){
    return new BCryptPasswordEncoder().matches(raw, this.passwordHash);
    }


}

/* ... */
