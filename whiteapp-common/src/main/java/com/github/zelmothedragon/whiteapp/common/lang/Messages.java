package com.github.zelmothedragon.whiteapp.common.lang;

import java.util.ResourceBundle;

/**
 * Support des messages internationalisés.
 *
 * @author MOSELLE Maxime
 */
public final class Messages {

    /**
     * Fichier des messages. Le fichier chargé dépend de la langue de la
     * <i>JVM</i>.
     */
    private static final ResourceBundle MESSAGE = ResourceBundle.getBundle("messages");

    /**
     * Constructeur interne. Pas d'instanciation.
     */
    private Messages() {
        throw new UnsupportedOperationException("Instance not allowed");
    }

    /**
     * Récupérer le message internationalisé.
     *
     * @param key Clef du message
     * @return Le message internationalisé, si la clef ne correspond à rien la
     * valeur <code>???key???</code> est retournée
     */
    public static String get(final String key) {
        String message;
        if (MESSAGE.containsKey(key)) {
            message = MESSAGE.getString(key);
        } else {
            message = "???" + key + "???";
        }
        return message;
    }

}
