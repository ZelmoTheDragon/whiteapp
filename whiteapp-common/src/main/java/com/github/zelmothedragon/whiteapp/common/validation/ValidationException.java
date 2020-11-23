package com.github.zelmothedragon.whiteapp.common.validation;

/**
 * Exception de validation d'objet.
 *
 * @author MOSELLE Maxime
 */
public final class ValidationException extends RuntimeException {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construit une exception de validation d'objet.
     *
     * @param targetClass Classe de l'objet où la validation a échouée
     * @see RuntimeException#RuntimeException(java.lang.String)
     */
    ValidationException(final Class<?> targetClass) {
	super("Validation fail for object: " + targetClass.getName());
    }

    /**
     * Construit une exception de validation d'objet.
     *
     * @param message Message d'erreur de validation
     * @see RuntimeException#RuntimeException(java.lang.String)
     */
    ValidationException(final String message) {
	super(message);
    }

}
