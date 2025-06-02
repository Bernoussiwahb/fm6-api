package com.fm6.fm6_api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

/**
 * Représente un enfant échangé via l’API.
 * Les annotations jakarta.validation activent la validation automatique.
 */
public class ChildDto {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @PastOrPresent(message = "La date de naissance ne peut pas être future")
    private LocalDate dateNaissance;

    /** Doit être soit 'actif' soit 'inactif'. */
    @Pattern(regexp = "actif|inactif", message = "Le statut doit être 'actif' ou 'inactif'")
    private String statut = "actif";

    /* ---- GETTERS & SETTERS ---- */
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate d) { this.dateNaissance = d; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}
