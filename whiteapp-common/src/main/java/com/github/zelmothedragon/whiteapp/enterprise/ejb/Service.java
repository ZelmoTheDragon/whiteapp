package com.github.zelmothedragon.whiteapp.enterprise.ejb;

import com.github.zelmothedragon.whiteapp.enterprise.persistence.Identifiable;
import com.github.zelmothedragon.whiteapp.enterprise.persistence.Pagination;
import java.util.List;
import java.util.Optional;

/**
 * Service métier commun.
 *
 * @author MOSELLE Maxime
 * @param <K> Type de l'identifiant unique
 * @param <E> Type de l'entité persistante
 */
public interface Service<K, E extends Identifiable<K>> {

    /**
     * Enregistrer une nouvelle entité. Si l'entité existe déjà, elle sera mise
     * à jour.
     *
     * @param entity Nouvelle entité
     * @return L'entité sauvegardée
     */
    E save(E entity);

    /**
     * Supprimer une entité.
     *
     * @param entity Entité métier
     */
    void remove(E entity);

    /**
     * Supprimer une entité en fonction de son identifiant unique.
     *
     * @param id Identifiant unique
     */
    void remove(K id);

    /**
     * Vérifier l'existence d'une entité.
     *
     * @param entity Entité à tester
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    boolean contains(E entity);

    /**
     * Vérifier l'existence d'une entité en fonction de son identifiant.
     *
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe, sinon la valeur
     * {@code false} est retournée
     */
    boolean contains(K id);

    /**
     * Supprimer toutes les entités.
     */
    void clear();

    /**
     * Rechercher une entité en fonction de son identifiant unique.
     *
     * @param id Identifiant unique
     * @return Une option contenant ou non l'entité correspondante à
     * l'identifiant unique
     */
    Optional<E> findFirst(K id);

    /**
     * Rechercher toutes les entités enregistrées.
     *
     * @return Une liste contenant toutes les occurrences
     */
    List<E> values();

    /**
     * Rechercher des entités enregistrés avec un critère de pagination.
     *
     * @param pagination Critère de pagination
     * @return Une liste des entités métiers
     */
    List<E> filter(Pagination pagination);

    /**
     * Rechercher des entités enregistrées en fonction d'un mot clef.
     *
     * @param keyword Mot clef pour la recherche
     * @return Une liste des entités persistantes
     */
    List<E> filter(String keyword);

}
