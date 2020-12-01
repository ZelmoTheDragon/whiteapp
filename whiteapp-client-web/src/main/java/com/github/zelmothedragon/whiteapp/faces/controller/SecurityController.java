package com.github.zelmothedragon.whiteapp.faces.controller;

import com.github.zelmothedragon.whiteapp.faces.Page;
import com.github.zelmothedragon.whiteapp.faces.model.Credential;
import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Contrôleur de connexion sécurisé.
 *
 * @author MOSELLE Maxime
 */
@Named
@RequestScoped
public class SecurityController {

    @Inject
    ExternalContext externalContext;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public SecurityController() {
        // Ne pas appeler explicitement.
    }

    /**
     * Connecter un utilisateur à partir des informations du formulaire.
     *
     * @param form Formulaire de connexion.
     * @return
     */
    public String login(final Credential form) {

        System.out.println("LOGIN:");
        System.out.println(form.getUsername());
        System.out.println(form.getPassword());
        return Page.HOME.redirect();
    }

    /**
     * Déconnecter l'utilisateur en session.
     */
    public void logout() {
        try {
            externalContext.invalidateSession();
            externalContext.redirect(Page.LOGIN.redirect());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
