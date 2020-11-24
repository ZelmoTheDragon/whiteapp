package com.github.zelmothedragon.whiteapp.common.lang;

import java.util.function.Function;

/**
 * Encapsule une fonction levant une exception.
 *
 * @author MOSELLE Maxime
 * @param <T> Type du paramètre d'entrée
 * @param <R> Type de retour
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {

    /**
     * Invoquer la fonction.
     *
     * @param param Paramètre d'entrée générique
     * @return Un résultat générique
     * @throws Exception Une exception générique à capturer
     */
    R apply(T param) throws Exception;

    /**
     * Encapsule une fonction de tel sorte à ne plus avoir besoin de capturer
     * d'exception. En cas d'erreur, l'exception devient de type
     * {@link RuntimeException}.
     *
     * @param <T> Type du paramètre d'entrée
     * @param <R> Type de retour
     * @param function Fonction quelconque passé par référence levant une
     * exception à capturer
     * @return Une fonction quelconque ne nécessitant plus de capturer une
     * exception
     */
    public static <T, R> Function<T, R> wrap(final CheckedFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception ex) {
                // Convertir l'exception en RuntimeException
                throw new IllegalStateException(ex);
            }
        };
    }
}
