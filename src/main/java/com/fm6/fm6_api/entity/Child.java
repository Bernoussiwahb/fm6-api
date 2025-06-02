package com.fm6.fm6_api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "children")
public class Child {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private User user;

    private String nom;
    private LocalDate dateNaissance;
    private String statut = "actif";

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate d) { this.dateNaissance = d; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
