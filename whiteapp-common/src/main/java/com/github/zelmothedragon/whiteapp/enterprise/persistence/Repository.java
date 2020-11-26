package com.github.zelmothedragon.whiteapp.enterprise.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Entrepôt de données pour les entités persistantes.
 *
 * @author MOSELLE Maxime
 * @param <K> Type de l'identifiant unique
 * @param <E> Type de l'entité persistante
 */
public interface Repository<K extends Serializable, E extends Identifiable<K>> {

    /**
     * Ajouter une nouvelle entité, si l'entité existe déjà alors l'occurrence
     * présente dans l'entrepôt est mise à jour.
     *
     * @param entity Nouvelle entité
     * @return L'instance de l'entité synchronisé
     */
    E add(E entity);

    /**
     * Supprimer une entité de l'entrepôt.
     *
     * @param entity Entité à supprimer
     */
    void remove(E entity);

    /**
     * Supprimer une entité de l'entrepôt en fonction de son identifiant unique.
     *
     * @param id Identifiant unique
     */
    void remove(K id);

    /**
     * Vérifier qu'une entité existe déjà dans l'entrepôt.
     *
     * @param entity Entité à tester
     * @return La valeur {@code true} si l'entité existe déjà, sinon la valeur
     * {@code false} est retournée
     */
    boolean contains(E entity);

    /**
     * Vérifier qu'une entité existe déjà dans l'entrepôt en fonction de son
     * identifiant unique.
     *
     * @param id Identifiant unique
     * @return La valeur {@code true} si l'entité existe déjà, sinon la valeur
     * {@code false} est retournée
     */
    boolean contains(K id);

    /**
     * Supprimer toutes les entités de l'entrepôt.
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
     * Rechercher toutes les entités de l'entrepôt.
     *
     * @return Une liste contenant toutes les occurrences
     */
    List<E> values();

    /**
     * Parcourir sous forme de séquence toutes les entités de l'entrepôt.
     *
     * @return Une séquence contenant toutes les occurrences
     */
    Stream<E> stream();

    /**
     * Rechercher des entités enregistrées avec un critère de pagination.
     *
     * @param pagination Critère de pagination
     * @return Une liste contenant toutes les occurrences filtrée
     */
    List<E> filter(Pagination pagination);

    /**
     * Rechercher des entités enregistrées en fonction d'un mot clef.
     *
     * @param keyword Mot clef pour la recherche
     * @return Une liste contenant toutes les occurrences filtrée
     */
    List<E> filter(String keyword);
}
