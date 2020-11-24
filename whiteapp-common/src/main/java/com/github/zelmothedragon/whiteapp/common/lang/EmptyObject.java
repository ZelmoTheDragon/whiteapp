package com.github.zelmothedragon.whiteapp.common.lang;

import java.util.UUID;

/**
 * Fournit des instances vides d'objets immuables.
 *
 * @author MOSELLE Maxime
 */
public final class EmptyObject {

    /**
     * Identifiant unique vide.
     */
    public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private EmptyObject() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

}
