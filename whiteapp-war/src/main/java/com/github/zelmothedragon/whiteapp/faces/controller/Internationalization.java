package com.github.zelmothedragon.whiteapp.faces.controller;

import com.github.zelmothedragon.whiteapp.faces.Page;
import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Internationalisation du site côté client.
 *
 * @author MOSELLE Maxime
 */
@Named
@SessionScoped
public class Internationalization implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Contexte de <i>Jakarta Faces</i>.
     */
    @Inject
    private FacesContext context;

    /**
     * Langue courante.
     */
    private Locale currentLocale;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public Internationalization() {
        this.currentLocale = Locale.FRENCH;
        // Ne pas appeler explicitement.
    }

    /**
     * Changer la langue côté client.
     *
     * @param lang Code de la nouvelle langue
     */
    public void changeLocale(final String lang) {

        switch (lang) {

            case "EN":
            case "en":
                currentLocale = Locale.ENGLISH;
                context
                        .getViewRoot()
                        .setLocale(Locale.ENGLISH);
                break;

            case "FR":
            case "fr":
            default:
                currentLocale = Locale.FRENCH;
                context
                        .getViewRoot()
                        .setLocale(Locale.FRENCH);

                break;
        }

        Page.reload();
    }

    // ------------------------------
    // Accesseurs & Mutateurs
    // ------------------------------
    public Locale getCurrentLocale() {
        return currentLocale;
    }

}
