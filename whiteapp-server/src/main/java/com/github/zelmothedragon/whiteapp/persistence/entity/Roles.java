package com.github.zelmothedragon.whiteapp.persistence.entity;

/**
 * Rôle disponibles dans l'application.
 *
 * @author MOSELLE Maxime
 */
public final class Roles {

    /**
     * Rôle d'invité.
     */
    public static final String GUEST = "GUEST";

    /**
     * Rôle d'administrateur.
     */
    public static final String ADMIN = "ADMIN";

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private Roles() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

}
