package com.github.zelmothedragon.whiteapp.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Générateur de ressource injectable.
 *
 * @author MOSELLE Maxime
 */
@ApplicationScoped
public class PersistenceResource {

    /**
     * Point de production d'injection du gestionnaire d'entité.
     */
    @Produces
    @PersistenceContext
    private EntityManager em;

    /**
     * Constructeur par défaut. Requis pour le fonctionnement des technologies
     * de Jakarta EE.
     */
    public PersistenceResource() {
        // Ne pas appeler explicitement.
    }

}
