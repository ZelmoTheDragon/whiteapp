package com.github.zelmothedragon.whiteapp.faces.model;

import javax.enterprise.inject.Model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Formulaire de connexion contenant l'identifiant de mot de passe d'un
 * utilisateur.
 *
 * @author MOSELLE Maxime
 */
@Model
public class Credential {

    /**
     * Identifiant de connexion.
     */
    @NotBlank
    @Size(min = 2, max = 255)
    private String username;

    /**
     * Mot de passe (en clair).
     */
    @NotBlank
    @Size(min = 2, max = 255)
    private String password;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public Credential() {
        // Ne pas appeler explicitement.
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
