package com.github.zelmothedragon.whiteapp.enterprise.persistence;

/**
 * Indique que les classes enfant sont identifiable par un identifiant unique.
 * Dans un modèle relationel, il s'agit de la clef primaire.
 *
 * @author MOSELLE Maxime
 * @param <K> Type de l'identifiant unique
 */
public interface Identifiable<K> {

    /**
     * Synchroniser l'identifiant unique avec le contexte de persistance.
     *
     * @return La valeur {@code true} si cette instance possède son identifiant
     * unique synchronisé, sinon la valeur {@code false} est retournée
     */
    boolean synchronizeId();

    /**
     * Obtenir l'identifiant unique de cette instance, ne doit jamais retourner
     * {@code null}.
     *
     * @return L'identifiant unique
     */
    K getId();

    /**
     * Modifier l'identifiant unique de cette instance. Cette méthode est utile
     * pour le fonctionnement de certaines technologies d'entreprise.
     *
     * @param id Nouvelle idenfiant unique non nul
     */
    void setId(K id);

}
