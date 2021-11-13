package com.github.zelmothedragon.whiteapp.faces.controller;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author MOSELLE Maxime
 */
@Named
@ViewScoped
public class DynamicController implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * d'entreprise.
     */
    public DynamicController() {
        // Ne pas appeler explicitement.
    }

}
